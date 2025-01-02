package com.example.carproject;

import com.example.carproject.models.Book;
import com.example.carproject.models.User;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserBrowseViewController
{


    public ArrayList<Book> books;
    @FXML
    public VBox record_container;
    @FXML
    public Button search_button;
    @FXML
    public TextArea search_field;
    @FXML
    public CheckBox only_availab_checkbox;

    public User user;

    //TODO: finish search buttons and improve the header label with the checkbox.
    //TODO: import icons for wypozycz and powiadom.
    //TODO: Idea - have a button that recommends you random non-occupied book. Also checkbox to see only non-occupied.
    @FXML
    public void initialize(){
        search_button.setOnAction(event->{
                books = fetchFiltered(search_field.getText());
                updateView();
        });
        only_availab_checkbox.setOnAction(event->{
            updateView();
        });
        updateView();
    }

    public UserBrowseViewController(){
        updateData();
    }


    public void updateData(){
        books = fetchBooks();
    }

    public void updateView(){

        record_container.getChildren().clear();
        for(Book b : books){
            //skip not available if checked.
            if(only_availab_checkbox.isSelected() && b.getRental_id() != 0){
                continue;
            }
            record_container.getChildren().add(generateRecord(b));
        }

    }

    ArrayList<Book> borrowBook(Book b){
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


            System.out.println("Zapytanie insert: "+successfull_insert);

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  result;
    }

    GridPane generateRecord(Book b){

        GridPane gridPane = new GridPane();

        // Ustawienia rozmiaru i widoczności linii
        gridPane.setGridLinesVisible(false); // Ukrycie linii siatki
        gridPane.setPadding(new Insets(10)); // Padding wokół siatki

        gridPane.getStyleClass().addAll("bookRentalPanel", "alert");


        int[] percent = new int[5];
        percent[0] = 5;
        percent[1]=30;
        percent[2] = 30;
        percent[3] = 15;
        percent[4] = 20;
        // Definiowanie kolumn z szerokościami procentowymi
        for (int i = 0; i < 5; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(percent[i]);
            gridPane.getColumnConstraints().add(columnConstraints);
        }

        // Tworzenie Label i ustawianie właściwości
        Label label1 = new Label(String.valueOf(b.getId()));
        label1.getStyleClass().add("userViewText");
        GridPane.setConstraints(label1, 0, 0); // Kolumna 0, Wiersz 0
        GridPane.setHalignment(label1, HPos.CENTER);

        Label label2 = new Label(b.getTitle());
        label2.getStyleClass().add("userViewText");
        GridPane.setConstraints(label2, 1, 0); // Kolumna 1, Wiersz 0
        GridPane.setHalignment(label2, HPos.CENTER);

        Label label3 = new Label(b.getAuthor());
        label3.getStyleClass().add("userViewText");
        GridPane.setConstraints(label3, 2, 0); // Kolumna 2, Wiersz 0
        GridPane.setHalignment(label3, HPos.CENTER);



        Label label4 = new Label();
        label4.getStyleClass().add("userViewText");
        label4.setPadding(new Insets(2));
        if(b.getRental_id() == 0){

            label4.setText("Dostepne");
            label4.getStyleClass().add("success");
            label4.getStyleClass().add("alert-success");
        }
        else{
            label4.setText("Niedostepne");
            label4.getStyleClass().add("danger");
            label4.getStyleClass().add("alert-danger");
        }

        GridPane.setConstraints(label4, 3, 0); // Kolumna 3, Wiersz 0
        GridPane.setHalignment(label4, HPos.CENTER);

        // Tworzenie przycisku i ustawianie właściwości
        Button button = new Button();
        if(b.getRental_id() == 0){
            button.setText("Wypozycz");
            button.setOnAction(event-> borrowBook(b));
        }
        else{
            button.setText("Powiadom");
        }
        button.getStyleClass().add("btn-primary");
        button.getStyleClass().add("btn");

        GridPane.setConstraints(button, 4, 0); // Kolumna 5, Wiersz 0
        GridPane.setHalignment(button, HPos.CENTER);

        // Dodawanie elementów do GridPane
        gridPane.getChildren().addAll(label1, label2, label3, label4, button);
        return gridPane;
    }




    ArrayList<Book> fetchFiltered(String filter){
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
