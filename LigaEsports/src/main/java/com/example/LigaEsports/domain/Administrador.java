package com.example.LigaEsports.domain;

import java.io.Serializable;
import java.util.UUID;
import java.util.random.RandomGenerator;

public class Administrador extends Utilizador implements Serializable {
    private static final long serialVersionUID = 1L;


    public Administrador(String name, String email,String username, String password) {
        super(email, name,username, password);
    }

    public Administrador() {
        super();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return nome;
    }

    public void setName(String name) {
        this.nome = name;
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
                ", name='" + nome + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
