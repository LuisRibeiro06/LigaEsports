package com.example.LigaEsports.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.random.RandomGenerator;

public class Torneio implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String name;
    private List<Team> teams;
    private Game game;
    private List<Partida> partidas;
    private List<Team> classificacao;

    public Torneio(String name, List<Team> teams, Game game, List<Partida> partidas, List<Team> classificacao) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.teams = teams;
        this.game = game;
        this.partidas = partidas;
        this.classificacao = classificacao;
    }

    public Torneio() {
        this.id = UUID.randomUUID();
        this.teams = new ArrayList<>();
        this.game = null;
        this.partidas = new ArrayList<>();
        this.classificacao = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Partida> getPartidas() {
        return partidas;
    }

    public void setPartidas(List<Partida> partidas) {
        this.partidas = partidas;
    }

    public List<Team> getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(List<Team> classificacao) {
        this.classificacao = classificacao;
    }

    @Override
    public String toString() {
        return "Torneio{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teams=" + teams +
                ", game=" + game +
                ", partidas=" + partidas +
                ", classificacao=" + classificacao +
                '}';
    }
}
