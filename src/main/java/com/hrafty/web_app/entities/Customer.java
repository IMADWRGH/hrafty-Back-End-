package com.hrafty.web_app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Table(name = "customer")
@EqualsAndHashCode(callSuper = true)
public class Customer extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String image;
    @Column(name = "sexe",length = 4,nullable = false,columnDefinition = "VARCHAR(4) ")
    private String sexe;
    @Column(name = "nbr_phone",length = 12,nullable = false,columnDefinition = "VARCHAR(12) ")
    private String phone;

    ////////////Relations///////
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(mappedBy = "customer")
    private Panel panel;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    @OneToMany(mappedBy = "customer")
    private List<Reviews> reviews;

    public Customer() {

    }

    public Customer(Long id, String image, String sexe, String phone, User user, Panel panel, List<Order> orders, List<Reviews> reviews) {
        this.id = id;
        this.image = image;
        this.sexe = sexe;
        this.phone = phone;
        this.user = user;
        this.panel = panel;
        this.orders = orders;
        this.reviews = reviews;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Panel getPanel() {
        return panel;
    }

    public void setPanel(Panel panel) {
        this.panel = panel;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Reviews> getReviews() {
        return reviews;
    }

    public void setReviews(List<Reviews> reviews) {
        this.reviews = reviews;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", sexe='" + sexe + '\'' +
                ", phone='" + phone + '\'' +
                ", user=" + user +
                ", panel=" + panel +
                ", orders=" + orders +
                ", reviews=" + reviews +
                '}';
    }
}
