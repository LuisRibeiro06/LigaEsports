package com.example.LigaEsports.repository;

import com.example.LigaEsports.domain.Administrador;
import com.example.LigaEsports.domain.Player;
import com.example.LigaEsports.domain.Treinador;
import com.example.LigaEsports.domain.Utilizador;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class UtilizadorRepository {

    private static final String FILE_NAME = "utilizadores.ser";
    private List<Utilizador> utilizadores;

    public UtilizadorRepository() {
        this.utilizadores = carregar();
    }

    public Treinador getTreinadorById(UUID id) {
        Utilizador u = getById(id);
        return (u instanceof Treinador) ? (Treinador) u : null;
    }

    public Player getPlayerById(UUID id) {
        Utilizador u = getById(id);
        return (u instanceof Player) ? (Player) u : null;
    }

    public List<Player> getAllPlayers() {
        return utilizadores.stream().filter(u -> u instanceof Player).map(u -> (Player) u).toList();
    }

    public Utilizador getById(UUID id) {
        return utilizadores.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
    }

    public void salvar(Utilizador u) {
        utilizadores.add(u);
        gravar();
    }

    public void remover(UUID id) {
        utilizadores.removeIf(u -> u.getId().equals(id));
        gravar();
    }

    public List<Utilizador> listarTodos() {
        return utilizadores;
    }

    private void gravar() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(utilizadores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private List<Utilizador> carregar() {
        File f = new File(FILE_NAME);
        if (!f.exists()) return new ArrayList<>();

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Utilizador>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
