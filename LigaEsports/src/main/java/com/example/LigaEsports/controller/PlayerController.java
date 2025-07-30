package com.example.LigaEsports.controller;

import com.example.LigaEsports.DTO.TorneioDTO;
import com.example.LigaEsports.Mapper.TorneioMapper;
import com.example.LigaEsports.domain.Player;
import com.example.LigaEsports.domain.Torneio;
import com.example.LigaEsports.service.PlayerService;
import com.example.LigaEsports.service.TorneioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/players")
//@CrossOrigin(origins = "http://localhost:5176")
public class PlayerController {

    private final PlayerService playerService;
    private final TorneioService torneioService;

    public PlayerController(PlayerService playerService, TorneioService TorneioService) {
        this.playerService = playerService;
        this.torneioService = TorneioService;
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
    public void apagar(@PathVariable UUID id) {
        playerService.apagar(id);
    }

    @GetMapping("/{id}/perfil")
    public Player verPerfil(@PathVariable UUID id) {
        return playerService.getById(id).orElse(null);
    }

    @PutMapping("/{id}/perfil")
    public Player editarPerfil(@PathVariable UUID id, @RequestBody Player atualizado) {
        atualizado.setId(id);
        playerService.atualizarPerfil(atualizado);
        return playerService.getById(id).orElse(null);
    }

    @GetMapping("/{id}/torneios")
    public List<TorneioDTO> listarTorneiosDoJogador(@PathVariable UUID id) {
        List<Torneio> torneios = torneioService.getTorneiosDoJogador(id);
        return torneios.stream().map(TorneioMapper::toDTO).toList();
    }

    @GetMapping("/{id}/estatisticas")
    public List<String> getEstatisticas(@PathVariable UUID id) {
        return playerService.getEstatisticas(id);
    }

}