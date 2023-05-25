module com.example.moviephlix {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;
    requires javafx.media;

    opens com.example.moviephlix to javafx.fxml;
    exports com.example.moviephlix;
    exports com.example.moviephlix.db;
    opens com.example.moviephlix.db to javafx.fxml;
    exports com.example.moviephlix.controller;
    opens com.example.moviephlix.controller to javafx.fxml;
    exports com.example.moviephlix.model;
    opens com.example.moviephlix.model to javafx.fxml;
}