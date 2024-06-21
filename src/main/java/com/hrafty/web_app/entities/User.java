package com.hrafty.web_app.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user")
@ToString
@EqualsAndHashCode(callSuper = false)
public class User extends Auditable  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "full_name",nullable = false)
    private String fullName;
    @Column(name = "email",nullable = false,unique = true)
    private String email;
   @Column(name = "password",nullable = false,unique = true)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role",updatable = false)
    private Role role;

    public User() {}

    public User(Long id, String fullName, String email, String password, Role role) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    /////Relations///////

    @OneToOne( cascade = CascadeType.ALL,orphanRemoval = true)
    private Seller seller;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    private Customer customer;

///////////////Getter & Setter/////////////////


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

   public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
