package com.example.DTO;

public class TorneioDTO {
    private Double id;
    private String nome;
    private int numEquipas;

    public TorneioDTO(Double id, String nome, int numEquipas) {
        this.id = id;
        this.nome = nome;
        this.numEquipas = numEquipas;
    }

    public Double getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getNumEquipas() {
        return numEquipas;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNumEquipas(int numEquipas) {
        this.numEquipas = numEquipas;
    }
}
