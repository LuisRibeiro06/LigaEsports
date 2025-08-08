package com.example.LigaEsports.DTO;

import com.example.LigaEsports.domain.Game;
import com.example.LigaEsports.domain.Position;

import java.util.UUID;

public class EstatisticaJogadorDTO {
    private UUID jogadorId;
    private Game tipoJogo; // "FPS", "MOBA", "EFOOTBALL"

    // FPS
    private Double precisao;
    private Integer headshots;

    // MOBA
    private String personagemPrincipal;
    private Integer kills;
    private Integer deaths;
    private Integer assists;

    // EFOOTBALL
    private Position posicao;
    private Integer golos;
    private Integer assistencias;

    // Getters e Setters

    public UUID getJogadorId() {
        return jogadorId;
    }

    public void setJogadorId(UUID jogadorId) {
        this.jogadorId = jogadorId;
    }

    public Game getTipoJogo() {
        return tipoJogo;
    }

    public void setTipoJogo(Game tipoJogo) {
        this.tipoJogo = tipoJogo;
    }

    public Double getPrecisao() {
        return precisao;
    }

    public void setPrecisao(Double precisao) {
        this.precisao = precisao;
    }

    public Integer getHeadshots() {
        return headshots;
    }

    public void setHeadshots(Integer headshots) {
        this.headshots = headshots;
    }

    public String getPersonagemPrincipal() {
        return personagemPrincipal;
    }

    public void setPersonagemPrincipal(String personagemPrincipal) {
        this.personagemPrincipal = personagemPrincipal;
    }

    public Integer getKills() {
        return kills;
    }

    public void setKills(Integer kills) {
        this.kills = kills;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }

    public Integer getAssists() {
        return assists;
    }

    public void setAssists(Integer assists) {
        this.assists = assists;
    }

    public Position getPosicao() {
        return posicao;
    }

    public void setPosicao(Position posicao) {
        this.posicao = posicao;
    }

    public Integer getGolos() {
        return golos;
    }

    public void setGolos(Integer golos) {
        this.golos = golos;
    }

    public Integer getAssistencias() {
        return assistencias;
    }

    public void setAssistencias(Integer assistencias) {
        this.assistencias = assistencias;
    }
}
