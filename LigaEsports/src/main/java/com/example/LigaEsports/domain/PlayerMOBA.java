package com.example.LigaEsports.domain;

import java.io.Serializable;
import java.util.List;

public class PlayerMOBA extends Player implements Serializable {
    private static final long serialVersionUID = 1L;

    private String personagem;
    private final String DEFAULT_PERSONAGEM = "Personagem";
    private int num_kills;
    private int num_deaths;
    private int num_assists;


    public PlayerMOBA(String name, String email, String username, String password) {
        super(name, email,username, password);
        this.personagem = DEFAULT_PERSONAGEM;
        this.num_kills = 0;
        this.num_deaths = 0;
        this.num_assists = 0;
    }

    public PlayerMOBA(Double id, String name) {
        super(name);
    }

    public PlayerMOBA() {
        super();
        this.personagem = DEFAULT_PERSONAGEM;
        this.num_kills = 0;
        this.num_deaths = 0;
        this.num_assists = 0;
    }

    @Override
    public Game getGame() {
        return Game.MOBA;
    }

    @Override
    public List<String> getStatistics() {
        return List.of("Personagem: "+ this.personagem +
                "Kills: "+ this.num_kills,
                "Deaths: "+ this.num_deaths,
                "Assists: "+ this.num_assists);
    }


    public String getPersonagem() {
        return personagem;
    }

    public void setPersonagem(String personagem) {
        this.personagem = personagem;
    }

    public int getNum_kills() {
        return num_kills;
    }

    public void setNum_kills(int num_kills) {
        this.num_kills = num_kills;
    }

    public int getNum_deaths() {
        return num_deaths;
    }

    public void setNum_deaths(int num_deaths) {
        this.num_deaths = num_deaths;
    }

    public int getNum_assists() {
        return num_assists;
    }

    public void setNum_assists(int num_assists) {
        this.num_assists = num_assists;
    }
}
