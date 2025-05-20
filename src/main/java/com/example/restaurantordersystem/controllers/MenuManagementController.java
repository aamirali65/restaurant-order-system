package com.example.restaurantordersystem.controllers;

import java.util.Optional;

import com.example.restaurantordersystem.model.MenuItem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class MenuManagementController {
    @FXML private TextField searchField;
    @FXML private ComboBox<String> categoryFilter;
    @FXML private TableView<MenuItem> menuItemsTable;
    @FXML private TableColumn<MenuItem, Integer> idColumn;
    @FXML private TableColumn<MenuItem, String> nameColumn;
    @FXML private TableColumn<MenuItem, String> descriptionColumn;
    @FXML private TableColumn<MenuItem, Double> priceColumn;
    @FXML private TableColumn<MenuItem, String> categoryColumn;
    @FXML private TableColumn<MenuItem, Boolean> availableColumn;
    @FXML private TableColumn<MenuItem, Void> actionsColumn;

    private ObservableList<MenuItem> menuItems = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        availableColumn.setCellValueFactory(new PropertyValueFactory<>("available"));

        // Initialize category filter
        categoryFilter.setItems(FXCollections.observableArrayList(
            "Appetizers", "Main Course", "Desserts", "Beverages"
        ));

        // Set up actions column
        actionsColumn.setCellFactory(col -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");

            {
                editButton.setOnAction(e -> handleEditItem(getTableRow().getItem()));
                deleteButton.setOnAction(e -> handleDeleteItem(getTableRow().getItem()));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(5, editButton, deleteButton);
                    setGraphic(buttons);
                }
            }
        });

        // Load initial data
        loadMenuItems();
    }

    private void loadMenuItems() {
        // TODO: Load menu items from your data source
        // For now, we'll use dummy data
        menuItems.add(new MenuItem(1, "Margherita Pizza", "Classic tomato and mozzarella", 12.99, "Main Course"));
        menuItems.add(new MenuItem(2, "Caesar Salad", "Fresh romaine lettuce with Caesar dressing", 8.99, "Appetizers"));
        menuItemsTable.setItems(menuItems);
    }

    @FXML
    private void handleAddItem() {
        showItemDialog(null);
    }

    private void handleEditItem(MenuItem item) {
        showItemDialog(item);
    }

    private void handleDeleteItem(MenuItem item) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Delete Menu Item");
        alert.setContentText("Are you sure you want to delete " + item.getName() + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            menuItems.remove(item);
            // TODO: Remove from data source
        }
    }

    private void showItemDialog(MenuItem item) {
        Dialog<MenuItem> dialog = new Dialog<>();
        dialog.setTitle(item == null ? "Add Menu Item" : "Edit Menu Item");

        // TODO: Implement dialog content and logic
    }

    @FXML
    private void handleSearch() {
        String searchText = searchField.getText().toLowerCase();
        String selectedCategory = categoryFilter.getValue();

        menuItemsTable.setItems(menuItems.filtered(item -> {
            boolean matchesSearch = item.getName().toLowerCase().contains(searchText) ||
                                  item.getDescription().toLowerCase().contains(searchText);
            boolean matchesCategory = selectedCategory == null || 
                                    item.getCategory().equals(selectedCategory);
            return matchesSearch && matchesCategory;
        }));
    }
} 