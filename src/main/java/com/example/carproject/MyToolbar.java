package com.example.carproject;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

public class MyToolbar extends HBox {

    @FXML
    public Node content;

    public MyToolbar() {
        // Ustawienie odstępu między elementami w HBox
        this.setSpacing(10);
        this.getStyleClass().add("toolbar");

        // Tworzenie ImageView z obrazkiem
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/Fox.svg.png")));
        imageView.setFitWidth(32);
        imageView.setFitHeight(32);

        // Tworzenie tytułu w VBox
        VBox logoText = new VBox();
        logoText.setAlignment(Pos.CENTER_LEFT);
        Label label = new Label("LightLib");
        label.getStyleClass().add("h4");
        label.getStyleClass().add("text-white");
        logoText.getChildren().add(label);

        // Tworzenie TextArea i Button po prawej stronie
        TextArea searchField = new TextArea();
        searchField.setPrefHeight(5);
        searchField.setPrefWidth(100);

        Button searchButton = new Button("Search");
        searchButton.getStyleClass().add("btn");
        searchButton.getStyleClass().add("btn-warning");

        // Dodanie ImageView, VBox z logo, i Spacer
        this.getChildren().addAll(imageView, logoText);

        // Dodanie Region jako spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
        this.getChildren().add(spacer);

    }


}
