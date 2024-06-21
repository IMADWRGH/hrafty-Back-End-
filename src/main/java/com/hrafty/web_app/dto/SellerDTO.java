package com.hrafty.web_app.dto;


import com.hrafty.web_app.entities.User;



public class SellerDTO {
    private Long id;
    private Long nb_license;
    private String image;
    private String sexe;
    private String phone;
    private String site;
    private Long userId;

    public SellerDTO(Long id, Long nb_license, String image, String sexe, String phone, String site, Long userId) {
        this.id = id;
        this.nb_license = nb_license;
        this.image = image;
        this.sexe = sexe;
        this.phone = phone;
        this.site = site;
        this.userId = userId;
    }

    public SellerDTO() {
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "SellerDTO{" +
                "id=" + id +
                ", nb_id=" + nb_license +
                ", image='" + image + '\'' +
                ", sexe='" + sexe + '\'' +
                ", phone='" + phone + '\'' +
                ", site='" + site + '\'' +
                ", user=" + userId +
                '}';
    }
}

