package com.example.restaurantordersystem.model;

public class Table {
    private int tableNumber;
    private int capacity;
    private String status; // AVAILABLE, OCCUPIED, RESERVED
    private Order currentOrder;

    public Table(int tableNumber, int capacity) {
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.status = "AVAILABLE";
        this.currentOrder = null;
    }

    public void assignOrder(Order order) {
        this.currentOrder = order;
        this.status = "OCCUPIED";
    }

    public void clearTable() {
        this.currentOrder = null;
        this.status = "AVAILABLE";
    }

    // Getters and Setters
    public int getTableNumber() { return tableNumber; }
    public void setTableNumber(int tableNumber) { this.tableNumber = tableNumber; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Order getCurrentOrder() { return currentOrder; }
    public void setCurrentOrder(Order currentOrder) { this.currentOrder = currentOrder; }
} 