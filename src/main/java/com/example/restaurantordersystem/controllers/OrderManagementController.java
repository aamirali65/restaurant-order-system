package com.example.restaurantordersystem.controllers;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.example.restaurantordersystem.model.MenuItem;
import com.example.restaurantordersystem.model.Order;
import com.example.restaurantordersystem.model.OrderItem;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class OrderManagementController {
    @FXML private ComboBox<String> statusFilter;
    @FXML private TextField searchField;
    @FXML private TableView<Order> ordersTable;
    @FXML private TableColumn<Order, Integer> orderIdColumn;
    @FXML private TableColumn<Order, Integer> tableNumberColumn;
    @FXML private TableColumn<Order, String> itemsColumn;
    @FXML private TableColumn<Order, Double> totalColumn;
    @FXML private TableColumn<Order, String> statusColumn;
    @FXML private TableColumn<Order, String> timeColumn;
    @FXML private TableColumn<Order, Void> actionsColumn;

    @FXML private Label orderIdLabel;
    @FXML private Label tableNumberLabel;
    @FXML private Label orderStatusLabel;
    @FXML private Label orderTimeLabel;
    @FXML private ListView<OrderItem> orderItemsList;
    @FXML private Label orderTotalLabel;

    private ObservableList<Order> orders = FXCollections.observableArrayList();
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

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
        timeColumn.setCellValueFactory(cellData -> {
            Order order = cellData.getValue();
            return new SimpleStringProperty(order.getOrderTime().format(timeFormatter));
        });

        // Initialize status filter
        statusFilter.setItems(FXCollections.observableArrayList(
            "All", "PENDING", "PREPARING", "READY", "COMPLETED"
        ));
        statusFilter.setValue("All");

        // Set up actions column
        actionsColumn.setCellFactory(col -> new TableCell<>() {
            private final Button viewButton = new Button("View");
            private final Button editButton = new Button("Edit");

            {
                viewButton.setOnAction(e -> handleViewOrder(getTableRow().getItem()));
                editButton.setOnAction(e -> handleEditOrder(getTableRow().getItem()));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(5, viewButton, editButton);
                    setGraphic(buttons);
                }
            }
        });

        // Set up order selection listener
        ordersTable.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    displayOrderDetails(newSelection);
                }
            }
        );

        // Load initial data
        loadOrders();
    }

    private void loadOrders() {
        // TODO: Load orders from your data source
        // For now, we'll use dummy data
        Order order1 = new Order(1, 1);
        order1.addItem(new MenuItem(1, "Margherita Pizza", "Classic tomato and mozzarella", 12.99, "Main Course"), 2);
        orders.add(order1);
        ordersTable.setItems(orders);
    }

    private void displayOrderDetails(Order order) {
        orderIdLabel.setText("Order #" + order.getOrderId());
        tableNumberLabel.setText("Table #" + order.getTableNumber());
        orderStatusLabel.setText("Status: " + order.getStatus());
        orderTimeLabel.setText("Time: " + order.getOrderTime().format(timeFormatter));
        
        orderItemsList.setItems(FXCollections.observableArrayList(order.getItems()));
        orderTotalLabel.setText(String.format("Total: $%.2f", order.getTotalAmount()));
    }

    @FXML
    private void handleSearch() {
        String searchText = searchField.getText().toLowerCase();
        String selectedStatus = statusFilter.getValue();

        ordersTable.setItems(orders.filtered(order -> {
            boolean matchesSearch = String.valueOf(order.getOrderId()).contains(searchText) ||
                                  String.valueOf(order.getTableNumber()).contains(searchText);
            boolean matchesStatus = selectedStatus.equals("All") || 
                                  order.getStatus().equals(selectedStatus);
            return matchesSearch && matchesStatus;
        }));
    }

    @FXML
    private void handleUpdateStatus() {
        Order selectedOrder = ordersTable.getSelectionModel().getSelectedItem();
        if (selectedOrder == null) return;

        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Update Order Status");
        dialog.setHeaderText("Select new status for Order #" + selectedOrder.getOrderId());

        ComboBox<String> statusCombo = new ComboBox<>(FXCollections.observableArrayList(
            "PENDING", "PREPARING", "READY", "COMPLETED"
        ));
        statusCombo.setValue(selectedOrder.getStatus());

        dialog.getDialogPane().setContent(statusCombo);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(status -> {
            selectedOrder.setStatus(status);
            ordersTable.refresh();
            displayOrderDetails(selectedOrder);
        });
    }

    @FXML
    private void handlePrintBill() {
        Order selectedOrder = ordersTable.getSelectionModel().getSelectedItem();
        if (selectedOrder == null) return;

        // TODO: Implement bill printing
        System.out.println("Printing bill for Order #" + selectedOrder.getOrderId());
    }

    @FXML
    private void handleCloseOrder() {
        Order selectedOrder = ordersTable.getSelectionModel().getSelectedItem();
        if (selectedOrder == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Close Order");
        alert.setHeaderText("Close Order #" + selectedOrder.getOrderId());
        alert.setContentText("Are you sure you want to close this order?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            selectedOrder.setStatus("COMPLETED");
            ordersTable.refresh();
            displayOrderDetails(selectedOrder);
        }
    }

    private void handleViewOrder(Order order) {
        ordersTable.getSelectionModel().select(order);
    }

    private void handleEditOrder(Order order) {
        // TODO: Implement order editing
        System.out.println("Editing Order #" + order.getOrderId());
    }
} 