package com.sofball.demo.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@Entity
@Table(name = "roles", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"authority"})})
public class Role implements Serializable, GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @Id
    @Size(min = 1, max = 20)
    @Column(name = "authority", nullable = false, length = 20)
    private String authority;

    // default constructor
    public Role(){
    }

    // Getters and Setters

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
