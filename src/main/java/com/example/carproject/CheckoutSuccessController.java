package com.example.carproject;

import com.example.carproject.DataAccess.BookDb;
import com.example.carproject.models.BookRental;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class CheckoutSuccessController{

    public BookRental rental;

    public Text title_label;
    public Text author_label;
    public Button close_btn;
    public VBox container;

    @FXML
    public void initialize(){
        close_btn.setOnAction(event->{
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.close();
        });
    }

    public void updateView(){
        title_label.setText(rental.associatedBook.getTitle());
        author_label.setText(rental.associatedBook.getAuthor());

        if (BookDb.returnInTime(rental.getDeadline())){
            TextFlow textFlow1 = new TextFlow();
            textFlow1.setTextAlignment(TextAlignment.CENTER);


            Text text1 = new Text("Oddano w terminie: ");
            //Text text2 = new Text(rental.get);
            textFlow1.getChildren().addAll(text1, text2);


            TextFlow textFlow2 = new TextFlow();
            textFlow2.setTextAlignment(TextAlignment.CENTER);


            Text text3 = new Text("Punkty: ");
            text3.getStyleClass().add("text-danger");

            Text text4 = new Text("-10");
            text4.getStyleClass().add("text-danger");

            textFlow2.getChildren().addAll(text3, text4);

        }
    }
}
