package com.hrafty.web_app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hrafty.web_app.entities.enums.AccountStatus;
import com.hrafty.web_app.entities.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
@ToString
@EqualsAndHashCode(callSuper = false)
public class User extends Auditable implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email_verified", nullable = false)
    private boolean emailVerified = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = true)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status", nullable = false)
    private AccountStatus accountStatus = AccountStatus.PENDING;


    public User() {}

    public User(Long id, String fullName, String email, String password, boolean emailVerified, Role role, AccountStatus accountStatus, Seller seller, Customer customer) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.emailVerified = emailVerified;
        this.role = role;
        this.accountStatus = accountStatus;
        this.seller = seller;
        this.customer = customer;
    }

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    private Seller seller;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    private Customer customer;

    // Getters and Setters


    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

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
        if (role == null) return List.of();
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
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
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountStatus != AccountStatus.SUSPENDED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return accountStatus == AccountStatus.ACTIVE;
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

    @JsonIgnore
    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    @JsonIgnore
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
