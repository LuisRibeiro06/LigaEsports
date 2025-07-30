package com.example.LigaEsports.service;

import com.example.LigaEsports.domain.Partida;
import com.example.LigaEsports.domain.Team;
import com.example.LigaEsports.domain.Torneio;
import com.example.LigaEsports.repository.TeamRepository;
import com.example.LigaEsports.repository.TorneioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TorneioService {

    private final TorneioRepository repo = new TorneioRepository();
    private final TeamRepository teamRepo = new TeamRepository();

    public void inscreverEquipa(UUID torneioId, UUID equipaId) {
        Optional<Torneio> torneio = repo.getById(torneioId);
        Optional<Team> equipa = teamRepo.getById(equipaId);
        if (torneio.isPresent() && equipa.isPresent()) {
            torneio.get().getTeams().add(equipa.get());
            repo.salvar(torneio.get());
        }
    }

    public List<Torneio> getTorneiosDoJogador(UUID jogadorId) {
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

    public void agendarPartida(UUID torneioId, Partida partida) {
        Optional<Torneio> opt = getById(torneioId);
        opt.ifPresent(t -> {
            t.getPartidas().add(partida);
            salvar(t);
        });
    }

    public void alterarResultado(UUID id, Partida atualizado) {
        Optional<Torneio> opt = getById(id);
        opt.ifPresent(t -> {
            t.getPartidas().removeIf(p -> p.getId().equals(atualizado.getId()));
            t.getPartidas().add(atualizado);
                
            salvar(t);
        });
    }
    public String verEstatisticasTorneio(UUID id) {
        Optional<Torneio> opt = getById(id);
        if (opt.isEmpty()) return "Torneio não encontrado.";

        Torneio t = opt.get();
        StringBuilder sb = new StringBuilder();
        sb.append("📊 Estatísticas do Torneio: ").append(t.getName()).append("\n");
        sb.append("🔸 Número de Equipas: ").append(t.getTeams().size()).append("\n");
        sb.append("🔸 Número de Partidas: ").append(t.getPartidas().size()).append("\n");

        t.getPartidas().forEach(p -> {
            sb.append("   - ").append(p.getTeam1().getNome())
                    .append(" vs ").append(p.getTeam2().getNome())
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

    public void apagar(UUID id) {
        repo.apagar(id);
    }

    public Optional<Torneio> getById(UUID id) {
        return repo.getById(id);
    }


}