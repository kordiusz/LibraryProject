package com.example.carproject;

import com.example.carproject.models.BookRental;
import com.example.carproject.models.User;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class SceneManager
{
    public static <T> T makePopup(String popupPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(popupPath));
        Parent root = loader.load();

        Stage stage = new Stage();
        Scene scene = new Scene(root);

        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        scene.getStylesheets().add(SceneManager.class.getResource("/application.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
        return loader.getController();
    }

    public static <T> T switchView(Event event, String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(path));
        Parent root = loader.load();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        scene.getStylesheets().add(SceneManager.class.getResource("/application.css").toExternalForm());

        stage.setScene(scene);
        stage.show();


        return loader.getController();
    }

    public static void returnSuccessPopup(BookRental br) throws IOException {
        //TODO: checkout and return are confused XD need to change that in the future.
        CheckoutSuccessController controller = SceneManager.makePopup("CheckoutSuccessView.fxml");
        controller.rental = br;
        controller.updateView();
    }

    public static void borrowSuccessPopup(BookRental br) throws IOException {
        ReturnSuccessController controller = SceneManager.makePopup("ReturnSuccessView.fxml");
        controller.rental = br;
        controller.updateView();
    }

    public static void switchToDesktop(User u, MouseEvent event) throws IOException {

        UserDesktopController desktopController = SceneManager.switchView(event, "UserDesktopView.fxml");
        desktopController.user = u;
        desktopController.updateData();
        desktopController.updateView();
    }


}
