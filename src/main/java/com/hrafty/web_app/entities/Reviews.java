package com.hrafty.web_app.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "reviews")
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private byte rating;
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

    public Reviews(Long id, byte rating, String comment, Customer customer, Service service, Product product) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.customer = customer;
        this.service = service;
        this.product = product;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

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
