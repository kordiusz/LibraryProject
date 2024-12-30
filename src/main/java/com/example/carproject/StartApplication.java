package com.example.carproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.sql.*;

public class StartApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("UserDesktopView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);

        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());

        stage.setTitle("Igor's Library");
        stage.setScene(scene);
        stage.show();
    }

    private static void generateExampleDataIfNotExists(){
        String url = "jdbc:sqlite:my.db";


        // SQL statement for creating a new table
        String sqlUser =
                "CREATE TABLE IF NOT EXISTS user (" +
                        "    id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "    name TEXT NOT NULL," +
                        "    surname TEXT NOT NULL," +
                        "    creation_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                        "    points INTEGER DEFAULT 0" +
                        ");";
        String sqlBook =
                        "CREATE TABLE IF NOT EXISTS book (" +
                        "    id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "    title TEXT NOT NULL," +
                        "    author TEXT NOT NULL," +
                        "    borrow_count INTEGER DEFAULT 0," +
                        "    publish_date DATE NOT NULL" +
                        ");" ;
                String sqlRental =
                        "CREATE TABLE IF NOT EXISTS book_rental (" +
                        "    id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "    book_id INTEGER NOT NULL," +
                        "    user_id INTEGER NOT NULL," +
                        "    rent_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                        "    deadline TIMESTAMP NOT NULL," +
                        "    FOREIGN KEY (book_id) REFERENCES book(id)," +
                        "    FOREIGN KEY (user_id) REFERENCES user(id)" +
                        ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sqlUser);
            stmt.execute(sqlBook);
            stmt.execute(sqlRental);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }



    }

    public static void main(String[] args) {
        generateExampleDataIfNotExists();
        //launch();


    }
}