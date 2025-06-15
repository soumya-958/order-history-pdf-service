package org.services.orderhistorypdfservice.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(nullable = false)
    private double price;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    // Constructors
    public Order() {}

    public Order(String customerId, String itemName, double price, LocalDate orderDate) {
        this.customerId = customerId;
        this.itemName = itemName;
        this.price = price;
        this.orderDate = orderDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
}
