package com.hrafty.web_app.dto;

import java.util.List;

public class ReviewsDTO {
    private Long id;
    private String comments;
    private byte rating;
    private CustomerDTO customer;
    private List<ServiceDTO> service;

    public ReviewsDTO() {
    }

    public ReviewsDTO(Long id, String comments, byte rating, CustomerDTO customer, List<ServiceDTO> service) {
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

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public List<ServiceDTO> getService() {
        return service;
    }

    public void setService(List<ServiceDTO> service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return "ReviewsDTO{" +
                "id=" + id +
                ", comments='" + comments + '\'' +
                ", rating=" + rating +
                ", customer=" + customer +
                ", service=" + service +
                '}';
    }
}
