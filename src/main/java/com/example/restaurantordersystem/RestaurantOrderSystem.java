package com.example.restaurantordersystem;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class RestaurantOrderSystem extends Application {
    private BorderPane root;
    private VBox sidebar;
    private Label titleLabel;
    private VBox mainContent;

    @Override
    public void start(Stage primaryStage) {
        try {
            // Create the main layout
            root = new BorderPane();
            
            // Create sidebar
            sidebar = createSidebar();
            root.setLeft(sidebar);
            
            // Create title
            titleLabel = new Label("Restaurant Order System");
            titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
            titleLabel.setPadding(new Insets(20));
            root.setTop(titleLabel);
            
            // Create main content area
            mainContent = new VBox(20);
            mainContent.setPadding(new Insets(20));
            root.setCenter(mainContent);
            
            // Load initial view (Dashboard)
            loadView("dashboard");
            
            // Create the scene
            Scene scene = new Scene(root, 1200, 800);
            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
            
            // Set up the stage
            primaryStage.setTitle("Restaurant Order Management System");
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private VBox createSidebar() {
        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(20));
        sidebar.setPrefWidth(200);
        sidebar.setStyle("-fx-background-color: #2c3e50;");
        
        Label menuLabel = new Label("Menu");
        menuLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        menuLabel.setTextFill(Color.WHITE);
        
        Button[] menuButtons = {
            createMenuButton("Dashboard", "dashboard"),
            createMenuButton("Menu Management", "menu"),
            createMenuButton("Orders", "orders"),
            createMenuButton("Tables", "tables"),
            createMenuButton("Reports", "reports"),
            createMenuButton("Settings", "settings")
        };
        
        sidebar.getChildren().add(menuLabel);
        sidebar.getChildren().addAll(menuButtons);
        
        return sidebar;
    }

    private Button createMenuButton(String text, String viewName) {
        Button button = new Button(text);
        button.setId(viewName);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-alignment: CENTER-LEFT;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-alignment: CENTER-LEFT;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-alignment: CENTER-LEFT;"));
        button.setOnAction(e -> loadView(viewName));
        return button;
    }

    private void loadView(String viewName) {
        try {
            String fxmlPath = "/com/example/restaurantordersystem/views/" + viewName + ".fxml";
            URL resource = getClass().getResource(fxmlPath);
            if (resource == null) {
                throw new IOException("Cannot find FXML file: " + fxmlPath);
            }
            FXMLLoader loader = new FXMLLoader(resource);
            VBox view = loader.load();
            mainContent.getChildren().clear();
            mainContent.getChildren().add(view);
            
            // Update title
            titleLabel.setText(viewName.substring(0, 1).toUpperCase() + viewName.substring(1));
            
            // Update active button
            sidebar.getChildren().stream()
                .filter(node -> node instanceof Button)
                .map(node -> (Button) node)
                .forEach(button -> {
                    if (button.getId().equals(viewName)) {
                        button.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-alignment: CENTER-LEFT;");
                    } else {
                        button.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-alignment: CENTER-LEFT;");
                    }
                });
        } catch (IOException e) {
            e.printStackTrace();
            // Show error dialog
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to load view");
            alert.setContentText("Could not load the " + viewName + " view: " + e.getMessage());
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
} 