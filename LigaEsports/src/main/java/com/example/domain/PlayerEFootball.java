package com.example.domain;

import java.io.Serializable;

public class PlayerEFootball extends Player implements Serializable {
    private static final long serialVersionUID = 1L;

    private Position position;
    private int num_goals;
    private int num_assists;
    private int num_saves;

    public PlayerEFootball(Double id, String name, int num_games, int num_wins, int num_losses, Position position, int num_goals, int num_assists, int num_saves) {
        super(id, name, num_games, num_wins, num_losses);
        this.position = position;
        this.num_goals = num_goals;
        this.num_assists = num_assists;
        this.num_saves = num_saves;
    }

    public PlayerEFootball(Double id, String name) {
        super(id, name);
    }

    public PlayerEFootball() {
        super();
        this.position = Position.MIDFIELDER;
        this.num_goals = 0;
        this.num_assists = 0;
        this.num_saves = 0;
    }
}
