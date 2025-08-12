package com.example.LigaEsports.DTO;


import com.example.LigaEsports.domain.Position;
import jakarta.validation.constraints.Email;

public class PlayerUpdateDTO {

    private String name;

    @Email(message = "Email deve ter formato válido")
    private String email;

    private String username;

    // Campos específicos podem ser atualizados baseado no tipo do jogador
    private Double precisao;
    private String personagem;
    private Position position;

    // Getters e Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Double getPrecisao() { return precisao; }
    public void setPrecisao(Double precisao) { this.precisao = precisao; }

    public String getPersonagem() { return personagem; }
    public void setPersonagem(String personagem) { this.personagem = personagem; }

    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }
}