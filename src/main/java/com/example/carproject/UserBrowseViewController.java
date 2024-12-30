package com.example.carproject;

import com.example.carproject.models.Book;
import com.example.carproject.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserBrowseViewController
{


    public ArrayList<Book> books;
    //TODO: Idea - have a button that recommends you random non-occupied book. Also checkbox to see only non-occupied.


    public void updateData(){
        books = fetchBooks();
    }

    ArrayList<Book> fetchBooks(){
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

}
