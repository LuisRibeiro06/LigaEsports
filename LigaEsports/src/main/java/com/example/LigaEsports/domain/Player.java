package com.example.LigaEsports.domain;


import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.random.RandomGenerator;

public abstract class Player extends Utilizador implements Serializable {
    private static final long serialVersionUID = 1L;


    private final String DEFAULT_NAME = "Player";
    private int num_games;
    private int num_wins;
    private int num_losses;

    public Player(String name, String email, String username, String password) {
        super(name, email,username, password);
        this.num_games = 0;
        this.num_wins = 0;
        this.num_losses = 0;
    }


    public Player(String username) {
        super.setNome(DEFAULT_NAME);
        this.username = username;
        num_games = 0;
        num_wins = 0;
        num_losses = 0;
    }

    public Player() {
        super.setNome(DEFAULT_NAME);
        this.username = DEFAULT_NAME;
        num_games = 0;
        num_wins = 0;
        num_losses = 0;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public Role getRole() {
        return Role.PLAYER;
    }

    public abstract Game getGame();

    public abstract List<String> getStatistics();

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", Name ='" + super.getNome() + '\'' +
                ", Games =" + num_games +
                ", Victorys =" + num_wins +
                ", Loses =" + num_losses +
                '}';
    }


}
