package com.example.carproject;

import com.example.carproject.DataAccess.BookDb;
import com.example.carproject.models.BookRental;
import com.example.carproject.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class UserViewController {


    public VBox record_container;
    public ImageView logo_btn;

    ArrayList<BookRental> rented_books;

    @FXML
    public void initialize(){
        rented_books = BookDb.fetchRichRentalsFor(LoggedUser.current);
        logo_btn.setOnMouseClicked(event->{
            try {
                SceneManager.switchToDesktop(LoggedUser.current,event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void updateView(){
        record_container.getChildren().clear();
        for(BookRental br : rented_books){
            record_container.getChildren().add(generateRecord(br));
        }
    }
    public void updateData(){
        rented_books = BookDb.fetchRichRentalsFor(LoggedUser.current);
    }


    GridPane generateRecord(BookRental br){

        GridPane gridPane = new GridPane();


        gridPane.setGridLinesVisible(false);
        gridPane.setPadding(new Insets(10));

        gridPane.getStyleClass().addAll("bookRentalPanel", "alert");


        int[] percent = new int[6];
        percent[0] = 5;
        percent[1]=20;
        percent[2] = 20;
        percent[3] = 20;
        percent[4] = 15;
        percent[5] = 20;


        for (int i = 0; i < 6; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(percent[i]);
            gridPane.getColumnConstraints().add(columnConstraints);
        }

        // Tworzenie Label i ustawianie właściwości
        Label label1 = new Label(String.valueOf(br.associatedBook.getId()));
        label1.getStyleClass().add("userViewText");
        GridPane.setConstraints(label1, 0, 0); // Kolumna 0, Wiersz 0
        GridPane.setHalignment(label1, HPos.CENTER);

        Label label2 = new Label(br.associatedBook.getTitle());
        label2.getStyleClass().add("userViewText");
        GridPane.setConstraints(label2, 1, 0);
        GridPane.setHalignment(label2, HPos.CENTER);

        Label label3 = new Label(br.associatedBook.getAuthor());
        label3.getStyleClass().add("userViewText");
        GridPane.setConstraints(label3, 2, 0);
        GridPane.setHalignment(label3, HPos.CENTER);



        Label label4 = new Label();
        label4.getStyleClass().add("userViewText");
        label4.setPadding(new Insets(2));

        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        label4.setText(br.getDeadline().format(formatters));

        if(BookDb.returnInTime(br.getDeadline())){

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

        button.setOnAction(event->{
            BookDb.returnBook(br);
            updateData();
            updateView();
            try {
                SceneManager.returnSuccessPopup(br);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        GridPane.setConstraints(button, 4, 0);
        GridPane.setHalignment(button, HPos.CENTER);

        double total = (double) Duration.between(br.getRentTimestamp(), br.getDeadline()).toMinutes();
        double elapsed = (double) Duration.between(LocalDateTime.now(), br.getDeadline()).toMinutes();
        double ratio =  (total-elapsed)/ total;
        ProgressBar bar = new ProgressBar(ratio);
        GridPane.setConstraints(bar, 5,0);
        GridPane.setHalignment(bar, HPos.CENTER);

        bar.getStyleClass().addAll("progress-bar-primary", "progress-bar");


        gridPane.getChildren().addAll(label1, label2, label3, label4, button, bar);
        return gridPane;
    }

}
