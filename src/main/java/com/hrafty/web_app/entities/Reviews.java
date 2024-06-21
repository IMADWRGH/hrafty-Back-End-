package com.hrafty.web_app.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "reviews")
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "comment")
    private String comments;
    @Column(name = "rating")
    private byte rating;


    ////Relations////////

    @ManyToOne
    @JoinColumn( name = "customer_id")
    private Customer customer;


    @OneToMany(mappedBy = "reviews")
    private List<Service> service;

    ///////////////


    public Reviews() {
    }

    public Reviews(Long id, String comments, byte rating, Customer customer, List<Service> service) {
        this.id = id;
        this.comments = comments;
        this.rating = rating;
        this.customer = customer;
        this.service = service;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public byte getRating() {
        return rating;
    }

    public void setRating(byte rating) {
        this.rating = rating;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Service> getService() {
        return service;
    }

    public void setService(List<Service> service) {
        this.service = service;
    }
}
