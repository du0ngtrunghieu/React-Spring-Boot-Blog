package com.trunghieu.common.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trunghieu.common.model.User;
import com.trunghieu.common.util.BlogENUM;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created on 9/4/2020.
 * Class: UserPrincipal.java
 * By : Admin
 */

public class UserPrincipal implements UserDetails {
    private Long id;

    private String name;

    private String username;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;

    private String description;

    private String avatar;

    private BlogENUM.STATUS status;


    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Long id, String name, String username, String email, String password, String description, String avatar, BlogENUM.STATUS status, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.description = description;
        this.avatar = avatar;
        this.status = status;
        this.authorities = authorities;
    }

    public static UserPrincipal create(User user) {
//        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
//                new SimpleGrantedAuthority(role.getName().name())
//        ).collect(Collectors.toList());
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getName()));
        return new UserPrincipal(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getDescription(),
                user.getAvatar(),
                user.getStatus(),
                authorities
        );
    }

    public String getDescription() {
        return description;
    }

    public String getAvatar() {
        return avatar;
    }

    public BlogENUM.STATUS getStatus() {
        return status;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
