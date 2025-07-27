package com.example.domain;

import java.io.Serializable;
import java.util.random.RandomGenerator;

public class Administrador extends Utilizador implements Serializable {
    private static final long serialVersionUID = 1L;

    private Double id;
    private String name;
    private String email;

    public Administrador(Double id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Administrador() {
        this.id = RandomGenerator.getDefault().nextDouble();
    }

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Role getRole() {
        return Role.ADMIN;
    }

    @Override
    public String toString() {
        return "Administrador{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
