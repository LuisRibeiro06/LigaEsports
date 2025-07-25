package com.example.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

public class Team implements Serializable {
    private static final long serialVersionUID = 1L;

    private Double id;
    private String name;
    private List<Player> players;
    private int vitorias;
    private int derrotas;

    public Team(Double id, String name, List<Player> players, int vitorias, int derrotas) {
        this.id = id;
        this.name = name;
        this.players = players;
        this.vitorias = vitorias;
        this.derrotas = derrotas;
    }

    public Team() {
        this.id = RandomGenerator.getDefault().nextDouble();
        this.players = new ArrayList<>();
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

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getVitorias() {
        return vitorias;
    }

    public void setVitorias(int vitorias) {
        this.vitorias = vitorias;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public void setDerrotas(int derrotas) {
        this.derrotas = derrotas;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", players=" + players +
                ", vitorias=" + vitorias +
                ", derrotas=" + derrotas +
                '}';
    }
}
