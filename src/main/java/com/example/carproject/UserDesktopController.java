package com.example.carproject;

import com.example.carproject.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class UserDesktopController
{

    public User user;

    public Label points_label;
    public Text full_name_label;
    public Text rented_total_label;
    public Text timestamp_label;
    public Button browse_btn;
    public Button borrow_btn;
    public ImageView logo_btn;

    @FXML
    public void initialize(){

        browse_btn.setOnAction(event->{
            try {
                switchToBrowse(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        borrow_btn.setOnAction(event->{
            try{
                switchToBorrowed(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void updateData(){
        LoggedUser.updateUserData();
        //TODO: Make all calls to user that are not static - static.
        user = LoggedUser.current;
    }

    //Update using model.
    public void updateView(){
        points_label.setText(String.valueOf(user.getPoints()));
        full_name_label.setText(String.format("%s %s", user.getName(), user.getSurname()));
        rented_total_label.setText(String.valueOf(user.getRentedTotal()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        timestamp_label.setText(user.getCreationTimestamp().format(formatter));
    }

    void switchToBrowse(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserBrowseView.fxml"));
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
        UserBrowseViewController browseController = loader.getController();
        browseController.user = LoggedUser.current;
        browseController.updateData();
        browseController.updateView();
    }

    void switchToBorrowed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserView.fxml"));
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
        UserViewController borrowedController = loader.getController();
        borrowedController.updateView();
    }
}
