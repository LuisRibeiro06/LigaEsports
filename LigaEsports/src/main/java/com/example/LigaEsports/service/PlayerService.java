package com.example.LigaEsports.service;

import com.example.LigaEsports.DTO.EstatisticaJogadorDTO;
import com.example.LigaEsports.domain.Player;
import com.example.LigaEsports.domain.PlayerEFootball;
import com.example.LigaEsports.domain.PlayerFPS;
import com.example.LigaEsports.domain.PlayerMOBA;
import com.example.LigaEsports.repository.PlayerRepository;
import com.example.LigaEsports.repository.UtilizadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlayerService {

    private final PlayerRepository repo = new PlayerRepository();
    private final UtilizadorRepository utilizadorRepo = new UtilizadorRepository();

    public List<Player> listar() {
        return utilizadorRepo.getAllPlayers();
    }

    public void salvar(Player p) {
        repo.salvar(p);
    }

    public Optional<Player> getById(UUID id) {
        return repo.listarTodos().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public void atualizarPerfil(Player atualizado) {
        apagar(atualizado.getId());
        salvar(atualizado);
    }

    public void apagar(UUID id) {
        repo.remover(id);
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
        Optional<Player> p = getById(id);
        return p.map(player -> List.of(
                "Partidas: " + player.getNum_games(),
                "Vitórias: " + player.getNum_wins(),
                "Derrotas: " + player.getNum_losses()
        )).orElse(List.of("Jogador não encontrado"));
    }
}