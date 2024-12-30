package com.example.carproject;

import com.example.carproject.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.time.format.DateTimeFormatter;

public class UserDesktopController
{

    public User user;

    public Label points_label;
    public Text full_name_label;
    public Text rented_total_label;
    public Text timestamp_label;


    //Update using model.
    public void update(){
        points_label.setText(String.valueOf(user.getPoints()));
        full_name_label.setText(String.format("%s %s", user.getName(), user.getSurname()));
        rented_total_label.setText(String.valueOf(user.getRentedTotal()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        timestamp_label.setText(user.getCreationTimestamp().format(formatter));
    }
}
