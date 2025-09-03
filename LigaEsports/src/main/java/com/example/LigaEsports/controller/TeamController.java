package com.example.LigaEsports.controller;

import com.example.LigaEsports.DTO.PlayerResponseDTO;
import com.example.LigaEsports.Mapper.PlayerMapper;
import com.example.LigaEsports.domain.Player;
import com.example.LigaEsports.domain.Team;
import com.example.LigaEsports.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/treinadores/{treinadorId}/team")
//@CrossOrigin(origins = "http://localhost:5176")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/jogadores/sem-equipa")
    public ResponseEntity<List<PlayerResponseDTO>> listarJogadoresSemEquipa() {
        List<Player> players = teamService.listarJogadoresSemEquipa();
        return ResponseEntity.ok(players.stream().map(PlayerMapper::toResponseDTO).toList());
    }

    @PostMapping
    public Team criarEquipa(@PathVariable UUID treinadorId, @RequestBody String nomeEquipa) {
        return teamService.criarEquipa(treinadorId, nomeEquipa);
    }

    @GetMapping
    public Team verEquipa(@PathVariable UUID treinadorId) {
        return teamService.getByTreinadorId(treinadorId);
    }

    @PutMapping("/{equipaId}")
    public ResponseEntity<Void> editarEquipa(@PathVariable UUID equipaId, @RequestBody String nomeEquipa) {
        teamService.editarNomeEquipa(equipaId, nomeEquipa);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{equipaId}/adicionar-jogador/{jogadorId}")
    public ResponseEntity<Void> adicionarJogador(@PathVariable UUID equipaId, @PathVariable UUID jogadorId) {
        teamService.adicionarJogador(equipaId, jogadorId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/{equipaId}/remover-jogador/{jogadorId}")
    public ResponseEntity<Void> removerJogador(@PathVariable UUID equipaId, @PathVariable UUID jogadorId) {
        teamService.removerJogador(equipaId, jogadorId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
