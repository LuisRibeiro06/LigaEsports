package com.example.repository;

import com.example.domain.Team;
import com.example.domain.Torneio;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TorneioRepository {

    private static final String FILE_NAME = "torneios.ser";
    private List<Torneio> torneios;

    public TorneioRepository() {
        this.torneios = carregarDados();
    }

    public void salvar(Torneio t) {
        this.torneios.add(t);
        salvarDados();
    }

    public List<Torneio> listarTodas() {
        return this.torneios;
    }

    public void apagar(Double id) {
        this.torneios.removeIf(t -> t.getId().equals(id));
        salvarDados();
    }

    public Optional<Torneio> getById(Double id) {
        return this.torneios.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst();
    }



    private void salvarDados() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(torneios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Torneio> carregarDados() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Torneio>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}