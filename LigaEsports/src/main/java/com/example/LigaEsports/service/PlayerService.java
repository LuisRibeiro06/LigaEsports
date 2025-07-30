package com.example.LigaEsports.service;

import com.example.LigaEsports.domain.Player;
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

    public List<String> getEstatisticas(UUID id) {
        Optional<Player> p = getById(id);
        return p.map(player -> List.of(
                "Partidas: " + player.getNum_games(),
                "Vitórias: " + player.getNum_wins(),
                "Derrotas: " + player.getNum_losses()
        )).orElse(List.of("Jogador não encontrado"));
    }
}