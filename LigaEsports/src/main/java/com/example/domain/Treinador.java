package com.example.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

public class Treinador implements Serializable {
    private static final long serialVersionUID = 1L;

    private Double id;
    private String name;
    private String email;
    private Team team;

    public Treinador(Double id, String name, String email, Team teams) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.team = team;
    }

    public Treinador() {
        this.id = RandomGenerator.getDefault().nextDouble();
        this.team = null;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "Treinador{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", team=" + team +
                '}';
    }
}
