package com.example.carproject;

import com.example.carproject.DataAccess.ImageCompressor;
import com.example.carproject.models.Book;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class StartApplication extends Application {

    public static final String db_url = "jdbc:sqlite:my.db";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());

        stage.setTitle("Igor's Library");
        stage.setScene(scene);
        stage.show();
    }

    private static void generateDbStructureIfNotExists(){


        // SQL statement for creating a new table
        String sqlUser =
                "CREATE TABLE IF NOT EXISTS user (" +
                        "    id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "    nickname TEXT NOT NULL,"+
                        "    name TEXT NOT NULL," +
                        "    surname TEXT NOT NULL," +
                        "    creation_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                        "    total_rented INTEGER DEFAULT 0,"+
                        "    points INTEGER DEFAULT 0" +
                        ");";
        String sqlBook =
                        "CREATE TABLE IF NOT EXISTS book (" +
                        "    id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "  rental_id INTEGER,"+
                        "    title TEXT NOT NULL," +
                        "    author TEXT NOT NULL," +
                        "    borrow_count INTEGER DEFAULT 0," +
                        "    publish_date DATE NOT NULL," +
                                "cover BLOB," +
                                "FOREIGN KEY (rental_id) REFERENCES book_rental(id)"+
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


        try (Connection conn = DriverManager.getConnection(db_url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sqlUser);
            stmt.execute(sqlBook);
            stmt.execute(sqlRental);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }



    }



    public static void addImagesToDb() throws IOException {


        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book(4, "1984", "George Orwell", 0, null, 0));
        books.add(new Book(5, "Wielki Gatsby", "Francis Scott Fitzgerald", 0, null, 0));
        books.add(new Book(6, "Zbrodnia i Kara", "Fiodor Dostojewski", 0, null, 0));

        File cover1 = new File("C:\\Users\\IgorK\\IdeaProjects\\CarProject\\src\\main\\resources\\1984.jpg");
        File cover2 = new File("C:\\Users\\IgorK\\IdeaProjects\\CarProject\\src\\main\\resources\\wielki_gatsby.webp");
        File cover3 = new File("C:\\Users\\IgorK\\IdeaProjects\\CarProject\\src\\main\\resources\\zbrodnia_kara.jpg");
        books.get(0).cover = ImageCompressor.compressImage(cover1, 0.7f);
        books.get(1).cover = ImageCompressor.compressImage(cover2, 0.7f);
        books.get(2).cover = ImageCompressor.compressImage(cover3, 0.7f);

        String query = "INSERT INTO book (id, title,author, borrow_count, publish_date, cover) VALUES (?,?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(db_url);
             ) {
            for(Book b : books){
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, b.getId());
                stmt.setString(2, b.getTitle());
                stmt.setString(3, b.getAuthor());
                stmt.setInt(4, 0);
                stmt.setDate(5, null);
                stmt.setBytes(6, b.cover);
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        //generateDbStructureIfNotExists();
        //addImagesToDb();
        launch();


    }
}