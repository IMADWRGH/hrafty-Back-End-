package com.hrafty.web_app.entities;

import jakarta.persistence.*;


@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private  Long id;
    private String street;
    private String shop_number;
    private String name_city;
    private String name_regional;

    public Address() {
    }

    public Address(Long id, String street, String shop_number, String name_city, String name_regional) {
        this.id = id;
        this.street = street;
        this.shop_number = shop_number;
        this.name_city = name_city;
        this.name_regional = name_regional;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getShop_number() {
        return shop_number;
    }

    public void setShop_number(String shop_number) {
        this.shop_number = shop_number;
    }

    public String getName_city() {
        return name_city;
    }

    public void setName_city(String name_city) {
        this.name_city = name_city;
    }

    public String getName_regional() {
        return name_regional;
    }

    public void setName_regional(String name_regional) {
        this.name_regional = name_regional;
    }
}
