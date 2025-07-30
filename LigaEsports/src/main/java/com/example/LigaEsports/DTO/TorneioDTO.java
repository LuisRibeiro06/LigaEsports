package com.example.LigaEsports.DTO;

import java.util.UUID;

public class TorneioDTO {
    private UUID id;
    private String nome;
    private int numEquipas;

    public TorneioDTO(UUID id, String nome, int numEquipas) {
        this.id = id;
        this.nome = nome;
        this.numEquipas = numEquipas;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getNumEquipas() {
        return numEquipas;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNumEquipas(int numEquipas) {
        this.numEquipas = numEquipas;
    }
}
