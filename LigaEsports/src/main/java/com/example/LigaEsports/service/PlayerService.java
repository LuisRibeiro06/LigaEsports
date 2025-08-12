package com.example.LigaEsports.service;

import com.example.LigaEsports.DTO.EstatisticaJogadorDTO;
import com.example.LigaEsports.domain.*;
import com.example.LigaEsports.repository.PlayerRepository;
import com.example.LigaEsports.repository.TeamRepository;
import com.example.LigaEsports.repository.UtilizadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlayerService {
    private final UtilizadorRepository utilizadorRepo;
    private final TeamRepository teamRepository;


    public PlayerService(UtilizadorRepository utilizadorRepo, TeamRepository teamRepository) {
        this.utilizadorRepo = utilizadorRepo;
        this.teamRepository = teamRepository;
    }

    public List<Player> listar() {
        return utilizadorRepo.getAllPlayers();
    }

    public void salvar(Player p) {
        utilizadorRepo.salvar(p);
    }

    public Optional<Player> getById(UUID id) {
        return listar().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public void atualizarPerfil(Player atualizado) {
        apagar(atualizado.getId());
        salvar(atualizado);
    }

    public void apagar(UUID id) {
        utilizadorRepo.remover(id);
    }

    public void atualizarEstatisticasDoJogador(EstatisticaJogadorDTO dto) {
        Player jogador = utilizadorRepo.getPlayerById(dto.getJogadorId());
        if (jogador == null) {
            throw new RuntimeException("Jogador nao encontrado");
        }

        jogador.setNum_games(jogador.getNum_games() + 1);

        switch (dto.getTipoJogo()) {
            case FPS:
                if (jogador instanceof PlayerFPS fps) {
                    fps.setPrecisao(dto.getPrecisao());
                    fps.setHeadshots(fps.getHeadshots() + dto.getHeadshots());
                }
                break;
            case MOBA:
                if (jogador instanceof PlayerMOBA moba) {
                    moba.setNum_kills(moba.getNum_kills() + dto.getKills());
                    moba.setNum_deaths(moba.getNum_deaths() + dto.getDeaths());
                    moba.setNum_assists(moba.getNum_assists() + dto.getAssists());
                    moba.setPersonagem(dto.getPersonagemPrincipal());
                }
                break;
            case EFOOTBALL:
                if (jogador instanceof PlayerEFootball ef) {
                    ef.setPosition(dto.getPosicao());
                    ef.setNum_goals(ef.getNum_goals() + dto.getGolos());
                    ef.setNum_assists(ef.getNum_assists() + dto.getAssistencias());
                }
                break;
        }

    }

    public List<String> getEstatisticas(UUID id) {
        Player jogador = utilizadorRepo.getPlayerById(id);
        if (jogador == null) {
            throw new RuntimeException("Jogador nao encontrado");
        }

        List<String> stats = jogador.getStatistics();
        stats.add(jogador.toString());
        return stats;
    }

    public Optional<Team> getEquipaDoJogador(UUID playerId) {
        return teamRepository.listarTodas().stream()
                .filter(team -> team.getPlayers().stream().anyMatch(p -> p.getId().equals(playerId)))
                .findFirst();
    }

}