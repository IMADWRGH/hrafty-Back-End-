package com.hrafty.web_app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hrafty.web_app.entities.enums.OrderStatus;
import com.hrafty.web_app.entities.enums.PaymentMethod;
import com.hrafty.web_app.entities.enums.PaymentStatus;
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
        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private OrderStatus status = OrderStatus.PENDING;

        @Enumerated(EnumType.STRING)
        private PaymentMethod paymentMethod;

        @Enumerated(EnumType.STRING)
        private PaymentStatus paymentStatus;

        private String transactionId;

        public Order() {
        }

        public Order(Long id, Customer customer, List<OrderItem> orderItems, double totalPrice, OrderStatus status) {
                this.id = id;
                this.customer = customer;
                this.orderItems = orderItems;
                this.totalPrice = totalPrice;
                this.status = status;
        }

        public OrderStatus getStatus() {
                return status;
        }

        public void setStatus(OrderStatus status) {
                this.status = status;
        }

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        @JsonIgnore
        public Customer getCustomer() {
                return customer;
        }

        public void setCustomer(Customer customer) {
                this.customer = customer;
        }

        @JsonIgnore
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
                        ", status=" + status +
                        '}';
        }
}
