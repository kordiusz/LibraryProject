package com.example.carproject;

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
                User user = tryFetchUser(username_field.getText());
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

    User tryFetchUser(String nick){

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
                return new User(id, name, surname, nickname, stamp.toLocalDateTime(), points);
            }


        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  null;
    }

    void switchToDesktop(User u, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserDesktopView.fxml"));
        Parent root = loader.load();


        UserDesktopController desktopController = loader.getController();
        desktopController.user = u;

        //TODO: Later on it would be better to have some form of scene manager, but for now its fine.
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
    }

}
