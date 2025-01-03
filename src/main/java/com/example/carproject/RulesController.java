package com.example.carproject;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class RulesController
{
    public ImageView logo_btn;

    @FXML
    public void initialize(){
        logo_btn.setOnMouseClicked(event->{
            try {
                SceneManager.switchToDesktop(LoggedUser.current, event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
