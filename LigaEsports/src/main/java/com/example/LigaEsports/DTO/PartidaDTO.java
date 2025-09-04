package com.example.LigaEsports.DTO;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PartidaDTO {
    private UUID id;
    private UUID equipe1Id, equipe2Id;
    private String nomeEquipa1, nomeEquipa2;
    private List<UUID> jogadoresEquipe1, jogadoresEquipe2;
    private String resultado;
    private Date data;

    public PartidaDTO(UUID id , UUID equipe1Id, String nomeEquipa1,UUID equipe2Id, String nomeEquipa2,String resultado, Date data) {
        this.id = id;
        this.equipe1Id = equipe1Id;
        this.nomeEquipa1 = nomeEquipa1;
        this.equipe2Id = equipe2Id;
        this.nomeEquipa2 = nomeEquipa2;
        this.resultado = resultado;
        this.data = data;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNomeEquipa1() {
        return nomeEquipa1;
    }

    public String getNomeEquipa2() {
        return nomeEquipa2;
    }

    public void setNomeEquipa1(String nomeEquipa1) {
        this.nomeEquipa1 = nomeEquipa1;
    }

    public void setNomeEquipa2(String nomeEquipa2) {
        this.nomeEquipa2 = nomeEquipa2;
    }

    public UUID getEquipe1Id() {
        return equipe1Id;
    }

    public UUID getEquipe2Id() {
        return equipe2Id;
    }

    public Date getData() {
        return data;
    }

    public void setEquipe1Id(UUID equipe1Id) {
        this.equipe1Id = equipe1Id;
    }

    public void setEquipe2Id(UUID equipe2Id) {
        this.equipe2Id = equipe2Id;
    }

    public List<UUID> getJogadoresEquipe1() {
        return jogadoresEquipe1;
    }

    public void setJogadoresEquipe1(List<UUID> jogadoresEquipe1) {
        this.jogadoresEquipe1 = jogadoresEquipe1;
    }

    public List<UUID> getJogadoresEquipe2() {
        return jogadoresEquipe2;
    }

    public void setJogadoresEquipe2(List<UUID> jogadoresEquipe2) {
        this.jogadoresEquipe2 = jogadoresEquipe2;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
