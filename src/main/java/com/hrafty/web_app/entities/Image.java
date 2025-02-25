package com.hrafty.web_app.entities;

import jakarta.persistence.*;

@Entity
@Table
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "filename")
    private String filename;
    @Column(name="url")
    private String url;


    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;




    public Image() {
    }

    public Image(Long id, String filename, String url, Service service, Product product) {
        this.id = id;
        this.filename = filename;
        this.url = url;
        this.service = service;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
