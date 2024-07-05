package com.hrafty.web_app.entities;

import jakarta.persistence.*;

import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Table(name = "seller")
@EqualsAndHashCode(callSuper = true)
public class Seller extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="number_id",unique = true,updatable = false,nullable = false)
    private Long nb_license;
    private String image;
    @Column(name = "sexe",length =4,nullable = false,columnDefinition = "VARCHAR(4) ")
    private String sexe;
    @Column(name = "nbr_phone",length = 12,nullable = false,columnDefinition = "VARCHAR(12) ")
    private String phone;
    private String  site;



    ////Relations////////
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "seller")
    private List<Service> services;

    @OneToMany(mappedBy = "seller")
    private List<Product> products;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;


    public Seller() {

    }
    public Seller(Long id, Long nb_license, String image, String sexe, String phone, String site, User user, List<Service> services, List<Product> products, Address address) {
        this.id = id;
        this.nb_license = nb_license;
        this.image = image;
        this.sexe = sexe;
        this.phone = phone;
        this.site = site;
        this.user = user;
        this.services = services;
        this.products = products;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNb_license() {
        return nb_license;
    }

    public void setNb_license(Long nb_license) {
        this.nb_license = nb_license;
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

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "id=" + id +
                ", nb_license=" + nb_license +
                ", image='" + image + '\'' +
                ", sexe='" + sexe + '\'' +
                ", phone='" + phone + '\'' +
                ", site='" + site + '\'' +
                ", user=" + user +
                ", services=" + services +
                ", products=" + products +
                ", address=" + address +
                '}';
    }
}
