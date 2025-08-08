package com.example.LigaEsports.DTO;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PartidaDTO {
    private UUID equipe1Id, equipe2Id;
    private List<UUID> jogadoresEquipe1, jogadoresEquipe2;
    private Date data;

    public PartidaDTO(UUID equipe1Id, UUID equipe2Id, Date data) {
        this.equipe1Id = equipe1Id;
        this.equipe2Id = equipe2Id;
        this.data = data;
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
