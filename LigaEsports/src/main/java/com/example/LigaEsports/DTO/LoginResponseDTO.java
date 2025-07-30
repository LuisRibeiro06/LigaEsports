package com.example.LigaEsports.DTO;

import com.example.LigaEsports.domain.Role;

import java.util.UUID;

public class LoginResponseDTO {
    private UUID id;
    private String username;
    private Role role;

    public LoginResponseDTO(UUID id, String nome, Role role) {
        this.id = id;
        this.username = nome;
        this.role = role;
    }



    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
