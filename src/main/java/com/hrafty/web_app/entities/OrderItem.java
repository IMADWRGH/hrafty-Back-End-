package com.hrafty.web_app.entities;

import jakarta.persistence.*;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Product product;
    private int quantity;
    private double totalPrice;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderItem() {
    }

    public OrderItem(Long id, Product product, int quantity, double totalPrice, Order order) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.order = order;
    }

    public OrderItem(Product product, int quantity, double totalPrice, Order order) {
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return totalPrice;
    }

    public void setPrice(double price) {
        this.totalPrice = price;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", product=" + product +
                ", quantity=" + quantity +
                ", price=" + totalPrice +
                '}';
    }
}
