package com.example.carproject;

import com.example.carproject.models.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.sql.*;

public class LoginController
{
    @FXML
    public TextArea username_field;
    @FXML
    public Button submit_btn;

    @FXML
    public void initialize(){

        submit_btn.setOnAction(event -> {
            if ( !username_field.getText().isBlank()) {
                User user = tryFetchUser(username_field.getText());
                System.out.println(user.getName());
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



}
