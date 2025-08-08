package com.example.LigaEsports.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.random.RandomGenerator;

public class Partida implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private Team team1;
    private List<Player> playersTeam1;
    private Team team2;
    private List<Player> playersTeam2;
    private String resultado;
    private Date data;

    public Partida(Team team1, Team team2, String resultado, Date data) {
        this.id = UUID.randomUUID();
        this.team1 = team1;
        this.team2 = team2;
        this.resultado = resultado;
        this.data = data;
    }

    public Partida() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public List<Player> getPlayersTeam1() {
        return playersTeam1;
    }

    public void setPlayersTeam1(List<Player> playersTeam1) {
        this.playersTeam1 = playersTeam1;
    }

    public List<Player> getPlayersTeam2() {
        return playersTeam2;
    }

    public void setPlayersTeam2(List<Player> playersTeam2) {
        this.playersTeam2 = playersTeam2;
    }

    @Override
    public String toString() {
        return "Partida{" +
                "id=" + id +
                ", team1=" + team1.getNome() +
                ", team2=" + team2.getNome() +
                ", resultado='" + resultado + '\'' +
                ", data=" + data +
                '}';
    }
}
