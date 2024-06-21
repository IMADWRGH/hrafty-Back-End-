package com.hrafty.web_app.dto;

import java.util.List;

public class PanelDTO {
    private Long id;
    private CustomerDTO customer;
    private List<Long> services;

    public PanelDTO() {
    }

    public PanelDTO(Long id, CustomerDTO customer, List<Long> services) {
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

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public List<Long> getServices() {
        return services;
    }

    public void setServices(List<Long> services) {
        this.services = services;
    }

    @Override
    public String toString() {
        return "PanelDTO{" +
                "id=" + id +
                ", customer=" + customer +
                ", services=" + services +
                '}';
    }
}
