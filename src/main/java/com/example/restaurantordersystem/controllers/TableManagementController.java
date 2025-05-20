package com.example.restaurantordersystem.controllers;

import java.util.Optional;

import com.example.restaurantordersystem.model.Table;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class TableManagementController {
    @FXML private GridPane tableGrid;
    @FXML private TextField tableNumberField;
    @FXML private TextField capacityField;
    @FXML private ComboBox<String> statusField;

    private ObservableList<Table> tables = FXCollections.observableArrayList();
    private static final int GRID_COLUMNS = 4;

    @FXML
    public void initialize() {
        // Initialize status options
        statusField.setItems(FXCollections.observableArrayList(
            "AVAILABLE", "OCCUPIED", "RESERVED"
        ));

        // Load initial data
        loadTables();
    }

    private void loadTables() {
        // TODO: Load tables from your data source
        // For now, we'll use dummy data
        for (int i = 1; i <= 12; i++) {
            tables.add(new Table(i, 4));
        }
        updateTableGrid();
    }

    private void updateTableGrid() {
        tableGrid.getChildren().clear();
        
        for (int i = 0; i < tables.size(); i++) {
            Table table = tables.get(i);
            VBox tableBox = createTableBox(table);
            
            int row = i / GRID_COLUMNS;
            int col = i % GRID_COLUMNS;
            tableGrid.add(tableBox, col, row);
        }
    }

    private VBox createTableBox(Table table) {
        VBox box = new VBox(10);
        box.setAlignment(Pos.CENTER);
        box.setPrefSize(150, 150);
        box.setStyle("-fx-background-color: " + getStatusColor(table.getStatus()) + "; " +
                    "-fx-background-radius: 10; " +
                    "-fx-padding: 10;");

        Label tableNumber = new Label("Table " + table.getTableNumber());
        tableNumber.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label capacity = new Label("Capacity: " + table.getCapacity());
        Label status = new Label(table.getStatus());
        status.setStyle("-fx-font-weight: bold;");

        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> handleEditTable(table));

        box.getChildren().addAll(tableNumber, capacity, status, editButton);
        return box;
    }

    private String getStatusColor(String status) {
        switch (status) {
            case "AVAILABLE": return "#27ae60";
            case "OCCUPIED": return "#e74c3c";
            case "RESERVED": return "#f39c12";
            default: return "#95a5a6";
        }
    }

    @FXML
    private void handleAddTable() {
        Dialog<Table> dialog = new Dialog<>();
        dialog.setTitle("Add New Table");
        dialog.setHeaderText("Enter table details");

        // Set up the dialog content
        tableNumberField.clear();
        capacityField.clear();
        statusField.setValue("AVAILABLE");

        dialog.getDialogPane().setContent(createTableForm());
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<Table> result = dialog.showAndWait();
        result.ifPresent(table -> {
            tables.add(table);
            updateTableGrid();
        });
    }

    private void handleEditTable(Table table) {
        Dialog<Table> dialog = new Dialog<>();
        dialog.setTitle("Edit Table");
        dialog.setHeaderText("Edit table details");

        // Set up the dialog content
        tableNumberField.setText(String.valueOf(table.getTableNumber()));
        capacityField.setText(String.valueOf(table.getCapacity()));
        statusField.setValue(table.getStatus());

        dialog.getDialogPane().setContent(createTableForm());
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<Table> result = dialog.showAndWait();
        result.ifPresent(updatedTable -> {
            table.setTableNumber(updatedTable.getTableNumber());
            table.setCapacity(updatedTable.getCapacity());
            table.setStatus(updatedTable.getStatus());
            updateTableGrid();
        });
    }

    private GridPane createTableForm() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Table Number:"), 0, 0);
        grid.add(tableNumberField, 1, 0);
        grid.add(new Label("Capacity:"), 0, 1);
        grid.add(capacityField, 1, 1);
        grid.add(new Label("Status:"), 0, 2);
        grid.add(statusField, 1, 2);

        return grid;
    }
} 