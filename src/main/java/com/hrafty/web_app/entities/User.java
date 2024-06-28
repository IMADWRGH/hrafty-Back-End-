package com.hrafty.web_app.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.Collection;

@Entity
@Table(name = "user")
@ToString
@EqualsAndHashCode(callSuper = false)
public class User extends Auditable  implements UserDetails , Principal {
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

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
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

    @Override
    public String getName() {
        return email;
    }
}
