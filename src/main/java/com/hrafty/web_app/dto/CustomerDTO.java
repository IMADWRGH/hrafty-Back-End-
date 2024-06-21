package com.hrafty.web_app.dto;


import com.hrafty.web_app.entities.User;

import java.nio.file.attribute.UserPrincipal;

public class CustomerDTO {
    private Long id;
    private String image;
    private String sexe;
    private String phone;
    private Long userId;

    public CustomerDTO() {
    }

    public CustomerDTO(Long id, String image, String sexe, String phone, Long userId) {
        this.id = id;
        this.image = image;
        this.sexe = sexe;
        this.phone = phone;
        this.userId = userId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", sexe='" + sexe + '\'' +
                ", phone='" + phone + '\'' +
                ", userId=" + userId +
                '}';
    }
}
