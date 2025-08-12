package com.example.LigaEsports.domain;

import java.io.Serializable;
import java.util.List;

public class PlayerEFootball extends Player implements Serializable {
    private static final long serialVersionUID = 1L;

    private Position position;
    private int num_goals;
    private int num_assists;
    private int num_saves;

    public PlayerEFootball(String name,String email,String username, String password,Position position) {
        super(name,email,username,password);
        this.position = position;
        this.num_goals = 0;
        this.num_assists = 0;
        this.num_saves = 0;
    }

    public PlayerEFootball(String name,String email,String username, String password) {
        super(name,email,username,password);
        this.position = Position.MIDFIELDER;
        this.num_goals = 0;
        this.num_assists = 0;
        this.num_saves = 0;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getNum_goals() {
        return num_goals;
    }

    public void setNum_goals(int num_goals) {
        this.num_goals = num_goals;
    }

    public int getNum_assists() {
        return num_assists;
    }

    public void setNum_assists(int num_assists) {
        this.num_assists = num_assists;
    }

    public int getNum_saves() {
        return num_saves;
    }

    public void setNum_saves(int num_saves) {
        this.num_saves = num_saves;
    }

    public PlayerEFootball(String name) {
        super(name);
    }

    public PlayerEFootball() {
        super();
        this.position = Position.MIDFIELDER;
        this.num_goals = 0;
        this.num_assists = 0;
        this.num_saves = 0;
    }

    @Override
    public Game getGame() {
        return Game.EFOOTBALL;
    }

    @Override
    public List<String> getStatistics() {
        return List.of("Golos : "+ this.num_goals,
                "Assistencias : "+ this.num_assists,
                "Defesas : "+ this.num_saves);
    }
}
