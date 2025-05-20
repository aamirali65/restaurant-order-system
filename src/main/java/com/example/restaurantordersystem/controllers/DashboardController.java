package com.example.restaurantordersystem.controllers;

import com.example.restaurantordersystem.model.Order;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DashboardController {
    @FXML private Label activeOrdersCount;
    @FXML private Label availableTablesCount;
    @FXML private Label todayRevenue;
    @FXML private TableView<Order> recentOrdersTable;
    @FXML private TableColumn<Order, Integer> orderIdColumn;
    @FXML private TableColumn<Order, Integer> tableNumberColumn;
    @FXML private TableColumn<Order, String> itemsColumn;
    @FXML private TableColumn<Order, Double> totalColumn;
    @FXML private TableColumn<Order, String> statusColumn;

    @FXML
    public void initialize() {
        // Initialize table columns
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        tableNumberColumn.setCellValueFactory(new PropertyValueFactory<>("tableNumber"));
        itemsColumn.setCellValueFactory(cellData -> {
            Order order = cellData.getValue();
            return new SimpleStringProperty(order.getItems().size() + " items");
        });
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Load initial data
        updateDashboard();
    }

    private void updateDashboard() {
        // TODO: Implement data loading from your data source
        // For now, we'll use dummy data
        activeOrdersCount.setText("5");
        availableTablesCount.setText("8");
        todayRevenue.setText("$1,234.56");
    }

    @FXML
    private void handleNewOrder() {
        // TODO: Implement new order creation
        System.out.println("New Order clicked");
    }

    @FXML
    private void handleViewMenu() {
        // TODO: Implement menu view
        System.out.println("View Menu clicked");
    }

    @FXML
    private void handleManageTables() {
        // TODO: Implement table management
        System.out.println("Manage Tables clicked");
    }
} 