package com.example.LigaEsports.domain;

import java.io.Serializable;
import java.util.UUID;
import java.util.random.RandomGenerator;

public class Treinador extends Utilizador implements Serializable {
    private static final long serialVersionUID = 1L;

    public Treinador( String name, String email,String username, String password) {
        super(name, email,username, password);
    }

    public Treinador() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public Role getRole() {
        return Role.TREINADOR;
    }


    @Override
    public String toString() {
        return "Treinador{" +
                "id=" + id +
                ", name='" + super.getNome() + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


}
