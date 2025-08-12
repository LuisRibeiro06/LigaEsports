package com.example.LigaEsports.domain;

import java.io.Serializable;
import java.util.List;

public class PlayerFPS extends Player implements Serializable {
    private static final long serialVersionUID = 1L;

    private Double precisao;
    private int headshots;

    public PlayerFPS(String name, String email,String username, String password) {
        super(name, email,username, password);
        this.precisao = 0.0;
        this.headshots = 0;
    }

    public PlayerFPS() {
        super();
        this.precisao = 0.0;
        this.headshots = 0;
    }

    @Override
    public Game getGame() {
        return Game.FPS;
    }

    @Override
    public List<String> getStatistics() {
        return List.of(
                "Headshots: " + headshots,
                "Taxa de precisão: " + precisao + "%"
        );
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
