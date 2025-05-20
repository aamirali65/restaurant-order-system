module com.example.restaurantordersystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.media;
    requires javafx.swing;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;

    opens com.example.restaurantordersystem to javafx.fxml;
    opens com.example.restaurantordersystem.controllers to javafx.fxml;
    opens com.example.restaurantordersystem.model to javafx.fxml;
    
    exports com.example.restaurantordersystem;
    exports com.example.restaurantordersystem.controllers;
    exports com.example.restaurantordersystem.model;
}