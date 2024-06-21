package com.hrafty.web_app.entities;

import jakarta.persistence.*;


import java.util.List;

@Entity
@Table(name = "panel")
public class Panel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //////Relations/////////
    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Customer customer;

    @OneToMany(mappedBy = "panel")
    private List<Service> services;
    ////////////////////////////


    public Panel() {
    }

    public Panel(Long id, Customer customer, List<Service> services) {
        this.id = id;
        this.customer = customer;
        this.services = services;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}
