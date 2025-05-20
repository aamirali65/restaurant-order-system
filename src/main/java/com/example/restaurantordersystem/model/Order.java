package com.example.restaurantordersystem.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private int tableNumber;
    private List<OrderItem> items;
    private LocalDateTime orderTime;
    private String status; // PENDING, PREPARING, READY, COMPLETED
    private double totalAmount;

    public Order(int orderId, int tableNumber) {
        this.orderId = orderId;
        this.tableNumber = tableNumber;
        this.items = new ArrayList<>();
        this.orderTime = LocalDateTime.now();
        this.status = "PENDING";
        this.totalAmount = 0.0;
    }

    public void addItem(MenuItem menuItem, int quantity) {
        OrderItem orderItem = new OrderItem(menuItem, quantity);
        items.add(orderItem);
        calculateTotal();
    }

    private void calculateTotal() {
        totalAmount = items.stream()
                .mapToDouble(item -> item.getMenuItem().getPrice() * item.getQuantity())
                .sum();
    }

    // Getters and Setters
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getTableNumber() { return tableNumber; }
    public void setTableNumber(int tableNumber) { this.tableNumber = tableNumber; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { 
        this.items = items;
        calculateTotal();
    }

    public LocalDateTime getOrderTime() { return orderTime; }
    public void setOrderTime(LocalDateTime orderTime) { this.orderTime = orderTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getTotalAmount() { return totalAmount; }
} 