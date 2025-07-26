package com.example.service;

import com.example.domain.Player;
import com.example.domain.Team;
import com.example.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    private final TeamRepository repo = new TeamRepository();

    public List<Team> listarTodas() {
        return repo.listarTodas();
    }

    public Optional<Team> getById(Long id) {
        return repo.listarTodas().stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    public void salvar(Team equipa) {
        remover(equipa.getId()); // atualiza se já existir
        repo.salvar(equipa);
    }

    public void remover(Double id) {
        repo.apagar(id);
    }

    public void adicionarJogador(Double equipaId, Player jogador) {
        repo.adicionarJogador(equipaId, jogador);
    }

    public void removerJogador(Double equipaId, Player jogador) {
        repo.removerJogador(equipaId, jogador);
    }
}
