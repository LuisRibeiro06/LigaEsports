package com.example.service;

import com.example.domain.Game;
import com.example.domain.Partida;
import com.example.domain.Team;
import com.example.domain.Torneio;
import com.example.repository.TeamRepository;
import com.example.repository.TorneioRepository;

import java.util.List;
import java.util.Optional;

public class TorneioService {

    private final TorneioRepository repo = new TorneioRepository();
    private final TeamRepository teamRepo = new TeamRepository();

    public void inscreverEquipa(Double torneioId, Double equipaId) {
        Optional<Torneio> torneio = repo.getById(torneioId);
        Optional<Team> equipa = teamRepo.getById(equipaId);
        if (torneio.isPresent() && equipa.isPresent()) {
            torneio.get().getTeams().add(equipa.get());
            repo.salvar(torneio.get());
        }
    }

    public List<Torneio> getTorneiosDoJogador(Double jogadorId) {
        List<Torneio> torneios = listarTodos();
        List<Torneio> torneiosDoJogador = torneios.stream()
                .filter(torneio -> torneio.getTeams().stream()
                        .anyMatch(team -> team.getPlayers().stream()
                                .anyMatch(player -> player.getId().equals(jogadorId))))
                .toList();
        return torneiosDoJogador;
    }

    public List<Torneio> getTorneiosDaEquipa(Double equipaId) {
        return listarTodos().stream()
                .filter(t -> t.getTeams().stream().anyMatch(e -> e.getId().equals(equipaId)))
                .toList();
    }

    public List<Partida> getPartidasDaEquipa(Double equipaId) {
        return listarTodos().stream()
                .flatMap(t -> t.getPartidas().stream())
                .filter(p -> p.getTeam1().getId().equals(equipaId) || p.getTeam2().getId().equals(equipaId))
                .toList();
    }

    public void agendarPartida(Double torneioId, Partida partida) {
        Optional<Torneio> opt = getById(torneioId);
        opt.ifPresent(t -> {
            t.getPartidas().add(partida);
            salvar(t);
        });
    }

    public void alterarResultado(Double id, Partida atualizado) {
        Optional<Torneio> opt = getById(id);
        opt.ifPresent(t -> {
            t.getPartidas().removeIf(p -> p.getId().equals(atualizado.getId()));
            t.getPartidas().add(atualizado);
                
            salvar(t);
        });
    }
    public String verEstatisticasTorneio(Double id) {
        Optional<Torneio> opt = getById(id);
        if (opt.isEmpty()) return "Torneio não encontrado.";

        Torneio t = opt.get();
        StringBuilder sb = new StringBuilder();
        sb.append("📊 Estatísticas do Torneio: ").append(t.getName()).append("\n");
        sb.append("🔸 Número de Equipas: ").append(t.getTeams().size()).append("\n");
        sb.append("🔸 Número de Partidas: ").append(t.getPartidas().size()).append("\n");

        t.getPartidas().forEach(p -> {
            sb.append("   - ").append(p.getTeam1().getName())
                    .append(" vs ").append(p.getTeam2().getName())
                    .append(" → Resultado: ").append(p.getResultado()).append("\n");
        });

        return sb.toString();
    }


    public List<Torneio> listarTodos() {
        return repo.listarTodas();
    }

    public void salvar(Torneio t) {
        repo.salvar(t);
    }

    public void apagar(Double id) {
        repo.apagar(id);
    }

    public Optional<Torneio> getById(Double id) {
        return repo.getById(id);
    }


}