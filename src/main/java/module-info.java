module com.example.carproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.carproject to javafx.fxml;
    exports com.example.carproject;
}