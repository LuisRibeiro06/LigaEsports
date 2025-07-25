package com.example.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

public class Torneio implements Serializable {
    private static final long serialVersionUID = 1L;

    private Double id;
    private String name;
    private List<Team> teams;
    private List<Game> game;
    private List<Partida> partidas;
    private List<Team> classificacao;

    public Torneio(Double id, String name, List<Team> teams, List<Game> game, List<Partida> partidas, List<Team> classificacao) {
        this.id = id;
        this.name = name;
        this.teams = teams;
        this.game = game;
        this.partidas = partidas;
        this.classificacao = classificacao;
    }

    public Torneio() {
        this.id = RandomGenerator.getDefault().nextDouble();
        this.teams = new ArrayList<>();
        this.game = new ArrayList<>();
        this.partidas = new ArrayList<>();
        this.classificacao = new ArrayList<>();
    }

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
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

    public List<Game> getGame() {
        return game;
    }

    public void setGame(List<Game> game) {
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
