package com.hrafty.web_app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hrafty.web_app.entities.enums.OrderStatus;
import com.hrafty.web_app.entities.enums.PaymentMethod;
import com.hrafty.web_app.entities.enums.PaymentStatus;
import jakarta.persistence.*;


import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders", indexes = {
        @Index(name = "idx_order_customer_status", columnList = "customer_id, order_status"),
        @Index(name = "idx_order_customer_date", columnList = "customer_id, order_date")
})
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
        @Column(nullable = false, name = "order_status")
        private OrderStatus status = OrderStatus.PENDING;

        @Column(name = "order_date")
        private LocalDateTime orderDate;

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

        public LocalDateTime getOrderDate() {
                return orderDate;
        }

        public void setOrderDate(LocalDateTime orderDate) {
                this.orderDate = orderDate;
        }

        public PaymentMethod getPaymentMethod() {
                return paymentMethod;
        }

        public void setPaymentMethod(PaymentMethod paymentMethod) {
                this.paymentMethod = paymentMethod;
        }

        public PaymentStatus getPaymentStatus() {
                return paymentStatus;
        }

        public void setPaymentStatus(PaymentStatus paymentStatus) {
                this.paymentStatus = paymentStatus;
        }

        public String getTransactionId() {
                return transactionId;
        }

        public void setTransactionId(String transactionId) {
                this.transactionId = transactionId;
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
