package com.example.carproject;

import com.example.carproject.DataAccess.BookDb;
import com.example.carproject.DataAccess.LibraryRules;
import com.example.carproject.models.BookRental;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;

public class ReturnSuccessController{

    public BookRental rental;

    public Text title_label;
    public Text author_label;
    public Button close_btn;
    public VBox container;

    @FXML
    public void initialize(){

    }

    public void updateView(){



        Label label = new Label("Wypozyczono ksiazke");
        label.setStyle("-fx-font-size: 20;");

        Label info = new Label(rental.associatedBook.getTitle() +" autorstwa " +rental.associatedBook.getAuthor());

        TextFlow textFlow1 = new TextFlow();
        textFlow1.setTextAlignment(TextAlignment.CENTER);

        Text text1 = new Text("Termin zwrotu do ");

        Text text2 = new Text();
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        text2.setText(rental.getDeadline().format(formatters));
        textFlow1.getChildren().addAll(text1, text2);



        //<Button text="Ok" styleClass="btn, btn-warning" style="-fx-padding: 10 0" fx:id="close_btn"/>
        Button closeButton = new Button("Ok");
        closeButton.getStyleClass().addAll("btn", "btn-warning");
        closeButton.setStyle("-fx-padding: 10 0;");

        closeButton.setOnAction(event->{
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.close();
        });

        container.getChildren().addAll(label,info, textFlow1, closeButton);
    }
}
