package com.hrafty.web_app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product" , indexes = {
        @Index(name = "idx_product_category_active", columnList = "category, is_active"),
        @Index(name = "idx_product_seller_active", columnList = "seller_id, is_active"),
        @Index(name = "idx_product_category_price", columnList = "category, price")
})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Image> images;
    @NotBlank @Size(min=2, max=100)
    private String name;
    @Column(length = 1000)
    private String description;
    @Min(0)
    private double price;
    private String category;
    @Column(nullable = false)
    private Integer stockQuantity = 0;
    @Column(nullable = false ,name = "is_active")
    private Boolean active = true;

    @ManyToOne
    private Seller seller;

    public Product() {
    }

    public Product(Long id, List<Image> images, String name, String description, double price, String category, Integer stockQuantity, Boolean active, Seller seller) {
        this.id = id;
        this.images = images;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.stockQuantity = stockQuantity;
        this.active = active;
        this.seller = seller;
    }


    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @JsonIgnore
    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @JsonIgnore
    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }
    public void addImage(Image image) {
        if (images == null) {
            images = new ArrayList<>();
        }
        images.add(image);
        image.setProduct(this);
    }

    public void removeImage(Image image) {
        images.remove(image);
        image.setProduct(null);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", images=" + images +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", stockQuantity=" + stockQuantity +
                ", active=" + active +
                ", seller=" + seller +
                '}';
    }
}
