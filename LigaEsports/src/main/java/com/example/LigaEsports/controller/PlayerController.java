package com.example.LigaEsports.controller;

import com.example.LigaEsports.DTO.*;
import com.example.LigaEsports.Mapper.PartidaMapper;
import com.example.LigaEsports.Mapper.PlayerMapper;
import com.example.LigaEsports.Mapper.TorneioMapper;
import com.example.LigaEsports.domain.Partida;
import com.example.LigaEsports.domain.Player;
import com.example.LigaEsports.domain.Team;
import com.example.LigaEsports.domain.Torneio;
import com.example.LigaEsports.service.PlayerService;
import com.example.LigaEsports.service.TorneioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/players")
//@CrossOrigin(origins = "http://localhost:5176")
public class PlayerController {

    private final PlayerService playerService;
    private final TorneioService torneioService;
    private final PlayerMapper playerMapper;

    public PlayerController(PlayerService playerService, TorneioService TorneioService, PlayerMapper playerMapper) {
        this.playerService = playerService;
        this.torneioService = TorneioService;
        this.playerMapper = playerMapper;
    }

    @GetMapping
    public List<PlayerResponseDTO> listar() {
        List<Player> players = playerService.listar();
        return players.stream()
                .map(playerMapper::toResponseDTO)
                .toList();
    }

    @GetMapping("/{id}/perfil")
    public PlayerResponseDTO verPerfil(@PathVariable UUID id) {
        return playerService.getById(id)
                .map(playerMapper::toResponseDTO)
                .orElse(null);
    }

    @PutMapping("/{id}/perfil")
    public PlayerResponseDTO editarPerfil(@PathVariable UUID id, @Valid @RequestBody PlayerUpdateDTO playerUpdateDTO) {
        Player existingPlayer = playerService.getById(id)
                .orElseThrow(() -> new RuntimeException("Jogador não encontrado"));

        Player playerToUpdate = playerMapper.updateFromDTO(existingPlayer, playerUpdateDTO);
        playerToUpdate.setId(id);

        playerService.atualizarPerfil(playerToUpdate);

        return playerService.getById(id)
                .map(playerMapper::toResponseDTO)
                .orElse(null);
    }

    @GetMapping("/{id}/torneios")
    public List<TorneioDTO> listarTorneiosDoJogador(@PathVariable UUID id) {
        List<Torneio> torneios = torneioService.getTorneiosDoJogador(id);
        return torneios.stream().map(TorneioMapper::toDTO).toList();
    }

    @GetMapping("/{id}/equipa")
    public Team getEquipaDoJogador(@PathVariable UUID id) {
        return playerService.getEquipaDoJogador(id).orElse(null);
    }


    @GetMapping("/{id}/estatisticas")
    public List<String> getEstatisticas(@PathVariable UUID id) {
        return playerService.getEstatisticas(id);
    }

    @GetMapping("/{playerId}/torneios/{torneioId}/partidas")
    public List<PartidaDTO> listarPartidasDoJogadorNoTorneio(
            @PathVariable UUID playerId,
            @PathVariable UUID torneioId
    ) {
        return torneioService.getPartidasDoJogadorNoTorneio(playerId, torneioId).stream().map(PartidaMapper::toDto).toList();
    }

}