package com.example.controller;

import com.example.DTO.TorneioDTO;
import com.example.Mapper.TorneioMapper;
import com.example.domain.Player;
import com.example.domain.Torneio;
import com.example.service.PlayerService;
import com.example.service.TorneioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
@CrossOrigin(origins = "http://localhost:3000")
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
    public void apagar(@PathVariable Double id) {
        playerService.apagar(id);
    }

    @GetMapping("/{id}/perfil")
    public Player verPerfil(@PathVariable Double id) {
        return playerService.getById(id).orElse(null);
    }

    @PutMapping("/{id}/perfil")
    public Player editarPerfil(@PathVariable Double id, @RequestBody Player atualizado) {
        atualizado.setId(id);
        playerService.atualizarPerfil(atualizado);
        return playerService.getById(id).orElse(null);
    }

    @GetMapping("/{id}/torneios")
    public List<TorneioDTO> listarTorneiosDoJogador(@PathVariable Double id) {
        List<Torneio> torneios = torneioService.getTorneiosDoJogador(id);
        return torneios.stream().map(TorneioMapper::toDTO).toList();
    }

    @GetMapping("/{id}/estatisticas")
    public List<String> getEstatisticas(@PathVariable Double id) {
        return playerService.getEstatisticas(id);
    }

}