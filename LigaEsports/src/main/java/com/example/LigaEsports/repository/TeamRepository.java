package com.example.LigaEsports.repository;

import com.example.LigaEsports.domain.Player;
import com.example.LigaEsports.domain.Team;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TeamRepository {

    private static final String FILE_NAME = "teams.ser";
    private List<Team> teams;

    public TeamRepository() {
        this.teams = carregarDados();
    }

    public Optional<Team> getById(UUID id) {
        return Optional.ofNullable(teams.stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null));
    }

    public Optional<Team> getByTreinadorId(UUID id) {
        return teams.stream().filter(t -> t.getTreinador() != null && t.getTreinador().getId().equals(id)).findFirst();
    }

    public void salvar(Team t) {
        teams.add(t);
        salvarDados();
    }

    public void apagar(UUID id) {
        teams.removeIf(t -> t.getId().equals(id));
        salvarDados();
    }

    public List<Team> listarTodas() {
        return teams;
    }

    public void adicionarJogador(UUID equipaId, Player jogador) {
        Team team = teams.stream().filter(t -> t.getId().equals(equipaId)).findFirst().orElse(null);
        if (team != null) {
            team.getPlayers().add(jogador);
            salvarDados();
        }
    }

    public void removerJogador(UUID equipaId, UUID jogadorId) {
        Team team = teams.stream().filter(t -> t.getId().equals(equipaId)).findFirst().orElse(null);
        if (team != null) {
            team.getPlayers().removeIf(p -> p.getId().equals(jogadorId));
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
