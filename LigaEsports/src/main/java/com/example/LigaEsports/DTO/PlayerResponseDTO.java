package com.example.LigaEsports.DTO;

import com.example.LigaEsports.domain.Game;
import com.example.LigaEsports.domain.Position;

import java.util.UUID;

public class PlayerResponseDTO {

    private UUID id;
    private String name;
    private String email;
    private String username;
    private Game tipoJogo;
    private Integer numGames;

    // Campos específicos que podem aparecer baseado no tipo
    private Double precisao;
    private Integer headshots;
    private String personagem;
    private Integer kills;
    private Integer deaths;
    private Integer assists;
    private Position position;
    private Integer goals;
    private Integer assistencias;

    // Getters e Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Game getTipoJogo() { return tipoJogo; }
    public void setTipoJogo(Game tipoJogo) { this.tipoJogo = tipoJogo; }

    public Integer getNumGames() { return numGames; }
    public void setNumGames(Integer numGames) { this.numGames = numGames; }

    public Double getPrecisao() { return precisao; }
    public void setPrecisao(Double precisao) { this.precisao = precisao; }

    public Integer getHeadshots() { return headshots; }
    public void setHeadshots(Integer headshots) { this.headshots = headshots; }

    public String getPersonagem() { return personagem; }
    public void setPersonagem(String personagem) { this.personagem = personagem; }

    public Integer getKills() { return kills; }
    public void setKills(Integer kills) { this.kills = kills; }

    public Integer getDeaths() { return deaths; }
    public void setDeaths(Integer deaths) { this.deaths = deaths; }

    public Integer getAssists() { return assists; }
    public void setAssists(Integer assists) { this.assists = assists; }

    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }

    public Integer getGoals() { return goals; }
    public void setGoals(Integer goals) { this.goals = goals; }

    public Integer getAssistencias() { return assistencias; }
    public void setAssistencias(Integer assistencias) { this.assistencias = assistencias; }
}