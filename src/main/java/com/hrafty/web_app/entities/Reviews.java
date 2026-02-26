package com.hrafty.web_app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "reviews")
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Min(1) @Max(5)
    private byte rating;
    @Column(nullable = false ,name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    private String comment;

    ////Relations////////

    @ManyToOne
    @JoinColumn( name = "customer_id")
    private Customer customer;

    @ManyToOne(optional = true)
    @JoinColumn(name = "service_id")
    private Service service;

    @ManyToOne(optional = true)
    @JoinColumn(name = "product_id")
    private Product product;

    ///////////////


    public Reviews() {
    }

    public Reviews(Long id, byte rating, LocalDateTime createdAt, String comment, Customer customer, Service service, Product product) {
        this.id = id;
        this.rating = rating;
        this.createdAt = createdAt;
        this.comment = comment;
        this.customer = customer;
        this.service = service;
        this.product = product;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte getRating() {
        return rating;
    }

    public void setRating(byte rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @JsonIgnore
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @JsonIgnore
    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @JsonIgnore
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Reviews{" +
                "id=" + id +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", customer=" + customer +
                ", service=" + service +
                ", product=" + product +
                '}';
    }
}
