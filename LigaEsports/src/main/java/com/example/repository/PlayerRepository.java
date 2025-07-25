package com.example.repository;

import com.example.domain.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerRepository {

    private static final String FILE_NAME = "players.ser";
    private List<Player> players;

    public PlayerRepository() {
        this.players = carregarDados();
    }

    public List<Player> listarTodos() {
        return players;
    }

    public void salvar(Player p) {
        players.add(p);
        gravarDados();
    }

    public void remover(Double id) {
        for ( Player p : players) {
            if (p.getId().equals(id)) {
                players.remove(p);
                break;
            }
        }
        gravarDados();
    }

    private void gravarDados() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(players);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private List<Player> carregarDados() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Player>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
