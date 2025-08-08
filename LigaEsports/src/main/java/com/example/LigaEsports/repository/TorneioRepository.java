package com.example.LigaEsports.repository;

import com.example.LigaEsports.domain.Torneio;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public class TorneioRepository {

    private static final String FILE_NAME = "torneios.ser";
    private List<Torneio> torneios;

    public TorneioRepository() {
        this.torneios = carregarDados();
    }

    public void salvar(Torneio t) {
        if (torneios.stream().noneMatch(torneio -> torneio.getId().equals(t.getId()))) {
            torneios.add(t);
        }
        salvarDados();
    }

    public List<Torneio> listarTodas() {
        return this.torneios;
    }

    public void apagar(UUID id) {
        this.torneios.removeIf(t -> t.getId().equals(id));
        salvarDados();
    }

    public Torneio getById(UUID id) {
        return this.torneios.stream()
                .filter(torneio -> torneio.getId().equals(id))
                .findFirst()
                .orElse(null);
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