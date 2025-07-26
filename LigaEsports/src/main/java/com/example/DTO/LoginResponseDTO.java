package com.example.DTO;

import com.example.domain.Role;

public class LoginResponseDTO {
    private Double id;
    private String username;
    private Role role;

    public LoginResponseDTO(Double id, String nome, Role role) {
        this.id = id;
        this.username = nome;
        this.role = role;
    }



    public Double getId() {
        return id;
    }

    public void setId(Double id) {
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
