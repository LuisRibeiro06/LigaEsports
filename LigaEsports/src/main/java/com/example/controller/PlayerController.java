package com.example.controller;

import com.example.domain.Player;
import com.example.domain.Team;
import com.example.domain.Torneio;
import com.example.service.PlayerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
@CrossOrigin(origins = "http://localhost:3000")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public List<Player> listar() {
        return playerService.listar();
    }

    @PostMapping
    public void criar(@RequestBody Player jogador) {
        playerService.salvar(jogador);
    }

    @DeleteMapping("/{id}")
    public void apagar(@PathVariable Long id) {
        playerService.apagar(id);
    }

    @GetMapping("/{id}/perfil")
    public Player verPerfil(@PathVariable Long id) { }

    @PutMapping("/{id}/perfil")
    public Player editarPerfil(@PathVariable Long id, @RequestBody Player atualizado) { }

    @GetMapping("/{id}/torneios")
    public List<Torneio> listarTorneiosDoJogador(@PathVariable Long id) { }

    @GetMapping("/{id}/estatisticas")
    public List<String> getEstatisticas(@PathVariable Long id) { }

    @GetMapping("/{id}/resultados")
    public List<Team> acompanharResultados(@PathVariable Long id) { }
}
