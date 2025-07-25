package com.example.domain;

import java.io.Serializable;

public class PlayerFPS extends Player implements Serializable {
    private static final long serialVersionUID = 1L;

    private Double precisao;
    private int headshots;

    public PlayerFPS(Double id, String name, int num_games, int num_wins, int num_losses, Double precisao, int headshots) {
        super(id, name, num_games, num_wins, num_losses);
        this.precisao = precisao;
        this.headshots = headshots;
    }

    public PlayerFPS(Double id, String name) {
        super(id, name);
    }

    public PlayerFPS() {
        super();
        this.precisao = 0.0;
        this.headshots = 0;
    }

    public Double getPrecisao() {
        return precisao;
    }

    public void setPrecisao(Double precisao) {
        this.precisao = precisao;
    }

    public int getHeadshots() {
        return headshots;
    }

    public void setHeadshots(int headshots) {
        this.headshots = headshots;
    }

    @Override
    public String toString() {
        return "PlayerFPS{" +
                "precisao=" + precisao +
                ", headshots=" + headshots +
                '}';
    }
}
