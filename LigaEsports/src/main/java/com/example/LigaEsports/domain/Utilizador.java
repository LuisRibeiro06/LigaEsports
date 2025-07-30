package com.example.LigaEsports.domain;

import java.io.Serializable;
import java.util.UUID;
import java.util.random.RandomGenerator;

public abstract class Utilizador implements Serializable {

    private static final long serialVersionUID = 1L;

    protected UUID id;
    protected String nome;
    protected String username;
    protected String email;
    protected String password;

    // Construtor vazio (necessário para serialização)
    public Utilizador() {
    }

    // Construtor completo
    public Utilizador(String nome, String email,String username, String password) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getters e Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract Role getRole();
}
