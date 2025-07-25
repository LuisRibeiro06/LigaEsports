package com.example.domain;


import java.io.Serializable;
import java.util.random.RandomGenerator;

public abstract class Player implements Serializable {
    private static final long serialVersionUID = 1L;

    private Double id;
    private String name;
    private final String DEFAULT_NAME = "Player";
    private int num_games;
    private int num_wins;
    private int num_losses;

    public Player(Double id, String name, int num_games, int num_wins, int num_losses) {
        this.id = id;
        this.name = name;
        this.num_games = num_games;
        this.num_wins = num_wins;
        this.num_losses = num_losses;
    }

    public Player(Double id, String name) {
        this.id = id;
        this.name = name;
    }

    public Player() {
        id = RandomGenerator.getDefault().nextDouble();
        name = DEFAULT_NAME;
        num_games = 0;
        num_wins = 0;
        num_losses = 0;
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

    public int getNum_games() {
        return num_games;
    }

    public void setNum_games(int num_games) {
        this.num_games = num_games;
    }

    public int getNum_wins() {
        return num_wins;
    }

    public void setNum_wins(int num_wins) {
        this.num_wins = num_wins;
    }

    public int getNum_losses() {
        return num_losses;
    }

    public void setNum_losses(int num_losses) {
        this.num_losses = num_losses;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", num_games=" + num_games +
                ", num_wins=" + num_wins +
                ", num_losses=" + num_losses +
                '}';
    }
}
