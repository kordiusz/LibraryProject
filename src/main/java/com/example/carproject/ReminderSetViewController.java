package com.example.carproject;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ReminderSetViewController {
    public Button close_btn;

    @FXML
    public void initialize(){
        close_btn.setOnAction(event->{
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.close();
        });
    }
}
