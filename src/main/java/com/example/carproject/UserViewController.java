package com.example.carproject;

import com.example.carproject.DataAccess.BookDb;
import com.example.carproject.models.Book;
import com.example.carproject.models.BookRental;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class UserViewController {


    public VBox record_container;

    ArrayList<BookRental> rented_books;

    @FXML
    public void initialize(){
        rented_books = BookDb.fetchRichRentalsFor(LoggedUser.current);
    }

    public void updateView(){

    }


    GridPane generateRecord(BookRental rental, Book b){

        GridPane gridPane = new GridPane();


        gridPane.setGridLinesVisible(false);
        gridPane.setPadding(new Insets(10));

        gridPane.getStyleClass().addAll("bookRentalPanel", "alert");


        int[] percent = new int[5];
        percent[0] = 5;
        percent[1]=30;
        percent[2] = 30;
        percent[3] = 15;
        percent[4] = 20;

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
        GridPane.setConstraints(label2, 1, 0);
        GridPane.setHalignment(label2, HPos.CENTER);

        Label label3 = new Label(b.getAuthor());
        label3.getStyleClass().add("userViewText");
        GridPane.setConstraints(label3, 2, 0);
        GridPane.setHalignment(label3, HPos.CENTER);



        Label label4 = new Label();
        label4.getStyleClass().add("userViewText");
        label4.setPadding(new Insets(2));

        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        label4.setText(rental.getDeadline().format(formatters));

        if(rental.getDeadline().isBefore(LocalDateTime.now())){

            label4.getStyleClass().add("success");
            label4.getStyleClass().add("alert-success");
        }
        else{
            label4.getStyleClass().add("danger");
            label4.getStyleClass().add("alert-danger");
        }

        GridPane.setConstraints(label4, 3, 0);
        GridPane.setHalignment(label4, HPos.CENTER);


        Button button = new Button("Zwrot");

        button.getStyleClass().add("btn-primary");
        button.getStyleClass().add("btn");

        GridPane.setConstraints(button, 4, 0);
        GridPane.setHalignment(button, HPos.CENTER);


        gridPane.getChildren().addAll(label1, label2, label3, label4, button);
        return gridPane;
    }
}
