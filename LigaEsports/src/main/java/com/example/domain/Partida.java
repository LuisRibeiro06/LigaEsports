package com.example.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.random.RandomGenerator;

public class Partida implements Serializable {
    private static final long serialVersionUID = 1L;

    private Double id;
    private Team team1;
    private Team team2;
    private String resultado;
    private Date data;

    public Partida(Double id, Team team1, Team team2, String resultado, Date data) {
        this.id = id;
        this.team1 = team1;
        this.team2 = team2;
        this.resultado = resultado;
        this.data = data;
    }

    public Partida() {
        this.id = RandomGenerator.getDefault().nextDouble();
    }

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
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

    @Override
    public String toString() {
        return "Partida{" +
                "id=" + id +
                ", team1=" + team1.getName() +
                ", team2=" + team2.getName() +
                ", resultado='" + resultado + '\'' +
                ", data=" + data +
                '}';
    }
}
