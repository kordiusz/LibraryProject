package com.example.carproject;

import com.example.carproject.DataAccess.BookDb;
import com.example.carproject.models.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.sql.*;

public class LoginController
{
    @FXML
    public TextArea username_field;
    @FXML
    public Button submit_btn;


    //TODO: later on add message when user is not found or there is an error.
    @FXML
    public void initialize(){

        submit_btn.setOnAction(event -> {
            if ( !username_field.getText().isBlank()) {
                User user = BookDb.tryFetchUser(username_field.getText());
                if (user != null){

                    try {
                        switchToDesktop(user,event);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }



    void switchToDesktop(User u, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserDesktopView.fxml"));
        Parent root = loader.load();

        //TODO: Later on it would be better to have some form of scene manager, but for now its fine.
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());

        stage.setScene(scene);
        stage.show();

        //TODO: no idea how to pass the data so its already in controller when initialize is executed.
        // But it would be nice to have it.
        UserDesktopController desktopController = loader.getController();
        desktopController.user = u;
        desktopController.update();
    }

}
