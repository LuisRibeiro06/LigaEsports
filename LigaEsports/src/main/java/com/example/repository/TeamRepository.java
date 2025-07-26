package com.example.repository;

import com.example.domain.Player;
import com.example.domain.Team;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TeamRepository {

    private static final String FILE_NAME = "teams.ser";
    private List<Team> teams;

    public TeamRepository() {
        this.teams = carregarDados();
    }

    public Optional<Team> getById(Double id) {
        return Optional.ofNullable(teams.stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null));
    }

    public void salvar(Team t) {
        teams.add(t);
        salvarDados();
    }

    public void apagar(Double id) {
        teams.removeIf(t -> t.getId().equals(id));
        salvarDados();
    }

    public List<Team> listarTodas() {
        return teams;
    }

    public void adicionarJogador(Double equipaId, Player jogador) {
        Team team = teams.stream().filter(t -> t.getId().equals(equipaId)).findFirst().orElse(null);
        if (team != null) {
            team.getPlayers().add(jogador);
            salvarDados();
        }
    }

    public void removerJogador(Double equipaId, Player jogador) {
        Team team = teams.stream().filter(t -> t.getId().equals(equipaId)).findFirst().orElse(null);
        if (team != null) {
            team.getPlayers().remove(jogador);
            salvarDados();
        }
    }

    private void salvarDados() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(teams);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private List<Team> carregarDados() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Team>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
