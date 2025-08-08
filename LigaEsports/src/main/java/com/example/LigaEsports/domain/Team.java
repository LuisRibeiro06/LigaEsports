package com.example.LigaEsports.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Team implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String nome;
    private Treinador treinador;
    private List<Player> players;
    private int vitorias;
    private int derrotas;

    public Team(String nome, List<Player> players, Treinador treinador) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.treinador = treinador;
        this.players = players;
        this.vitorias = 0;
        this.derrotas = 0;
    }

    public Team(String nome, Treinador treinador) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.treinador = treinador;
        this.players = new ArrayList<>();
        this.vitorias = 0;
        this.derrotas = 0;
    }

    public Team() {
        this.id = UUID.randomUUID();
        this.players = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public Treinador getTreinador() {
        return treinador;
    }

    public void setTreinador(Treinador treinador) {
        this.treinador = treinador;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", players=" + players +
                ", vitorias=" + vitorias +
                ", derrotas=" + derrotas +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(id, team.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
