package com.example.LigaEsports.service;

import com.example.LigaEsports.domain.Player;
import com.example.LigaEsports.domain.Team;
import com.example.LigaEsports.domain.Treinador;
import com.example.LigaEsports.repository.TeamRepository;
import com.example.LigaEsports.repository.UtilizadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TeamService {

    private final TeamRepository repo = new TeamRepository();
    private final UtilizadorRepository utilizadorRepo = new UtilizadorRepository();

    public List<Team> listarTodas() {
        return repo.listarTodas();
    }

    public Team criarEquipa(UUID treinadorId, String nomeEquipa) {
        try {
            Treinador treinador = utilizadorRepo.getTreinadorById(treinadorId);
            Team equipa = new Team(nomeEquipa);
            equipa.setTreinador(treinador);
            repo.salvar(equipa);
            return equipa;
        } catch (RuntimeException e) {
            throw new RuntimeException("Treinador não encontrado");
        }
    }

    public void editarNomeEquipa(UUID equipaId, String novoNome) {
        Optional<Team> optionalTeam = repo.getById(equipaId);
        if (optionalTeam.isEmpty()) {
            throw new RuntimeException("Equipa não encontrada");
        }

        Team equipa = optionalTeam.get();
        equipa.setNome(novoNome);
        repo.salvar(equipa);
    }

    public Optional<Team> getById(UUID id) {
        return listarTodas().stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    public Team getByTreinadorId(UUID id) {
        return repo.getByTreinadorId(id).orElse(null);
    }

    public void adicionarJogador(UUID equipaId, UUID jogadorId) {
        Player jogador = utilizadorRepo.getPlayerById(jogadorId);
        if (jogador == null) {
            throw new RuntimeException("Jogador nao encontrado");
        }
        repo.adicionarJogador(equipaId, jogador);
    }

    public void removerJogador(UUID equipaId, UUID jogadorId) {
        repo.removerJogador(equipaId, jogadorId);
    }
}
