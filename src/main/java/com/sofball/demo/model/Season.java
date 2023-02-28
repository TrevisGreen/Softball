package com.sofball.demo.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "seasons")
public class Season implements Serializable {

    @Id
    private Integer id;

    // Default constructor
    public Season() {
    }

    // Param constructor
    public Season(Integer id) {
        this.id = id;
    }

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
