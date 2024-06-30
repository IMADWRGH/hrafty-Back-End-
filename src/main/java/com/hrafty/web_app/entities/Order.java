package com.hrafty.web_app.entities;

import jakarta.persistence.*;


import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @ManyToOne
        private Customer customer;
        @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
        private List<OrderItem> orderItems;
        private double totalPrice;

        public Order() {
        }

        public Order(Long id, Customer customer, List<OrderItem> orderItems, double totalPrice) {
                this.id = id;
                this.customer = customer;
                this.orderItems = orderItems;
                this.totalPrice = totalPrice;
        }

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public Customer getCustomer() {
                return customer;
        }

        public void setCustomer(Customer customer) {
                this.customer = customer;
        }

        public List<OrderItem> getOrderItems() {
                return orderItems;
        }

        public void setOrderItems(List<OrderItem> orderItems) {
                this.orderItems = orderItems;
        }

        public double getTotalPrice() {
                return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
                this.totalPrice = totalPrice;
        }

        @Override
        public String toString() {
                return "Order{" +
                        "id=" + id +
                        ", customer=" + customer +
                        ", orderItems=" + orderItems +
                        ", totalPrice=" + totalPrice +
                        '}';
        }
}
