package com.example.domain;

import java.io.Serializable;

public class PlayerMOGA extends Player implements Serializable {
    private static final long serialVersionUID = 1L;

    private String personagem;
    private final String DEFAULT_PERSONAGEM = "Personagem";
    private int num_kills;
    private int num_deaths;
    private int num_assists;


    public PlayerMOGA(Double id, String name, int num_games, int num_wins, int num_losses, String personagem, int num_kills, int num_deaths, int num_assists) {
        super(id, name, num_games, num_wins, num_losses);
        this.personagem = personagem;
        this.num_kills = num_kills;
        this.num_deaths = num_deaths;
        this.num_assists = num_assists;
    }

    public PlayerMOGA(Double id, String name) {
        super(id, name);
    }

    public PlayerMOGA() {
        super();
        this.personagem = DEFAULT_PERSONAGEM;
        this.num_kills = 0;
        this.num_deaths = 0;
        this.num_assists = 0;
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
