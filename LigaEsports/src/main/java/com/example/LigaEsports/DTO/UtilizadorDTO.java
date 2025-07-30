package com.example.LigaEsports.DTO;

import com.example.LigaEsports.domain.Game;
import com.example.LigaEsports.domain.Role;

public class UtilizadorDTO {
    public String nome;
    public String email;
    public String username;
    public String password;
    public Role role;
    public Game game;

    public UtilizadorDTO() {}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
