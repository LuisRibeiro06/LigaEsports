package com.example.service;

import com.example.domain.Player;
import com.example.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository repo = new PlayerRepository();

    public List<Player> listar() {
        return repo.listarTodos();
    }

    public void salvar(Player p) {
        repo.salvar(p);
    }

    public Optional<Player> getById(Double id) {
        return repo.listarTodos().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public void atualizarPerfil(Player atualizado) {
        apagar(atualizado.getId());
        salvar(atualizado);
    }

    public void apagar(Double id) {
        repo.remover(id);
    }

    public List<String> getEstatisticas(Double id) {
        Optional<Player> p = getById(id);
        return p.map(player -> List.of(
                "Partidas: " + player.getNum_games(),
                "Vitórias: " + player.getNum_wins(),
                "Derrotas: " + player.getNum_losses()
        )).orElse(List.of("Jogador não encontrado"));
    }
}