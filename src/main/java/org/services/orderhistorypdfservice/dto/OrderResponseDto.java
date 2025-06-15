package org.services.orderhistorypdfservice.dto;

import java.time.LocalDate;

public class OrderResponseDto {
    private String itemName;
    private double price;
    private LocalDate orderDate;

    public OrderResponseDto(String itemName, double price, LocalDate orderDate) {
        this.itemName = itemName;
        this.price = price;
        this.orderDate = orderDate;
    }

    public String getItemName() {
        return itemName;
    }

    public double getPrice() {
        return price;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }
}
