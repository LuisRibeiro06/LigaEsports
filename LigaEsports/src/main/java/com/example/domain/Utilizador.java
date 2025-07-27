package com.example.domain;

import java.io.Serializable;

public abstract class Utilizador implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Double id;
    protected String nome;
    protected String email;
    protected String username;
    protected String password;

    // Construtor vazio (necessário para serialização)
    public Utilizador() {
    }

    // Construtor completo
    public Utilizador(Double id, String nome, String email, String username, String password) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    // Getters e Setters

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
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
