package com.example.carproject.DataAccess;

import com.example.carproject.LoggedUser;
import com.example.carproject.StartApplication;
import com.example.carproject.models.Book;
import com.example.carproject.models.BookRental;
import com.example.carproject.models.User;

import java.sql.*;
import java.util.ArrayList;

public class BookDb
{
    public static User tryFetchUser(String nick){

        String query = "SELECT * FROM USER WHERE NICKNAME = ?";

        try (Connection conn = DriverManager.getConnection(StartApplication.db_url)){
            PreparedStatement stmt = conn.prepareStatement(query) ;
            stmt.setString(1, nick);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                int id = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String nickname = rs.getString("nickname");
                Timestamp stamp = rs.getTimestamp("creation_timestamp");
                int points = rs.getInt("points");
                int rented_total = rs.getInt("total_rented");
                LoggedUser.current = new User(id, name, surname, nickname, stamp.toLocalDateTime(), points, rented_total);
                return LoggedUser.current;
            }


        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  null;
    }

    public static ArrayList<Book> fetchBooks(){
        String query = "SELECT * FROM BOOK LIMIT 15";
        ArrayList<Book> result = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(StartApplication.db_url)){
            PreparedStatement stmt = conn.prepareStatement(query) ;

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                Date date = rs.getDate("publish_date");
                int borrow_count = rs.getInt("borrow_count");
                int rental_id = rs.getInt("rental_id");
                result.add(new Book(id, title, author,  borrow_count, date.toLocalDate(),rental_id));
            }


        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  result;

    }

    public static ArrayList<BookRental> fetchRentalsFor(User user){
        String query = "SELECT * FROM book_rental WHERE user_id=? LIMIT 15";
        ArrayList<BookRental> result = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(StartApplication.db_url)){
            PreparedStatement stmt = conn.prepareStatement(query) ;
            stmt.setInt(1, user.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {



                int id = rs.getInt("id");
                int book_id = rs.getInt("book_id");
                int user_id = rs.getInt("user_id");
                Timestamp rent_stamp = rs.getTimestamp("rent_timestamp");
                Timestamp deadline_stamp = rs.getTimestamp("deadline");

                BookRental rental = new BookRental(id,book_id,user_id,rent_stamp.toLocalDateTime(),deadline_stamp.toLocalDateTime());
                result.add(rental);
            }


        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  result;
    }


    /*
    * Combines Rentals with Book data.
    * */
    public static ArrayList<BookRental> fetchRichRentalsFor(User user)
    {
            String query = "SELECT author,title, borrow_count, publish_date,book_rental.id, user_id,book_id, rent_timestamp,deadline FROM Book  INNER JOIN book_rental ON Book.id = book_rental.book_id WHERE book_rental.user_id = ? LIMIT 15;";
        ArrayList<BookRental> result = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(StartApplication.db_url)){
            PreparedStatement stmt = conn.prepareStatement(query) ;
            stmt.setInt(1, user.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                String title = rs.getString("title");
                String author = rs.getString("author");
                Date date = rs.getDate("publish_date");
                int borrow_count = rs.getInt("borrow_count");

                int id = rs.getInt("id");
                int book_id = rs.getInt("book_id");
                int user_id = rs.getInt("user_id");
                Timestamp rent_stamp = rs.getTimestamp("rent_timestamp");
                Timestamp deadline_stamp = rs.getTimestamp("deadline");

                BookRental rental = new BookRental(id,book_id,user_id,rent_stamp.toLocalDateTime(),deadline_stamp.toLocalDateTime());
                rental.associatedBook = new Book(book_id, title, author,  borrow_count, date.toLocalDate(),id);
                result.add(rental);
            }


        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  result;
    }

    public static ArrayList<Book> borrowBook(Book b, User user){
        ArrayList<Book> result = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(StartApplication.db_url)){

            //TODO: make time of rental based on points.
            String query = "INSERT INTO book_rental (book_id, user_id, deadline) VALUES (?,?,strftime('%Y-%m-%d %H:%M:%S', 'now', '+1 month'))";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, b.getId());
            stmt.setInt(2, user.getId());


            int successfull_insert = stmt.executeUpdate();

            if (successfull_insert > 0){
                try(ResultSet rs = stmt.getGeneratedKeys()){
                    if(rs.next()){
                        int newId = rs.getInt(1);

                        query = "UPDATE book SET rental_id = ? WHERE id = ?";
                        try(PreparedStatement book_update_stmt = conn.prepareStatement(query) ) {

                            book_update_stmt.setInt(1, newId);
                            book_update_stmt.setInt(2, b.getId());
                            book_update_stmt.executeUpdate();
                        }
                    }
                }
            }


        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  result;
    }

    public static ArrayList<Book> fetchFiltered(String filter){
        ArrayList<Book> result = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(StartApplication.db_url)){
            String query = "SELECT * FROM book WHERE title LIKE ? OR author LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + filter + "%");
            stmt.setString(2, "%" + filter + "%");


            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                Date date = rs.getDate("publish_date");
                int borrow_count = rs.getInt("borrow_count");
                int rental_id = rs.getInt("rental_id");
                result.add(new Book(id, title, author,  borrow_count, date.toLocalDate(),rental_id));
            }


        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  result;
    }
}
