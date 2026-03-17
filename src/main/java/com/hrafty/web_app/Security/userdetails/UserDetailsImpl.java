package com.hrafty.web_app.Security.userdetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hrafty.web_app.entities.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;

@Data
public class UserDetailsImpl implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String fullName;
    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;
    private boolean enabled;
    private boolean accountNonLocked;

    // ✅ Private constructor - forces use of build() method
    private UserDetailsImpl(Long id, String fullName, String email, String password,
                            Collection<? extends GrantedAuthority> authorities,
                            boolean enabled, boolean accountNonLocked) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
        this.accountNonLocked = accountNonLocked;
    }

    /**
     * Builds UserDetailsImpl from User entity
     */
    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = (user.getRole() != null)
                ? List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
                : List.of();

        return new UserDetailsImpl(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getPassword(),
                authorities,
                user.isEnabled(),
                user.isAccountNonLocked()
        );
    }

    // ✅ Implement ALL required UserDetails methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
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
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}