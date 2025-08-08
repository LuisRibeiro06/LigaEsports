package com.example.LigaEsports.controller;

import com.example.LigaEsports.domain.Player;
import com.example.LigaEsports.domain.Team;
import com.example.LigaEsports.service.TeamService;
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
    public List<Player> listarJogadoresSemEquipa() {
        return teamService.listarJogadoresSemEquipa();
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
    public void editarEquipa(@PathVariable UUID equipaId, @RequestBody String nomeEquipa) {
        teamService.editarNomeEquipa(equipaId, nomeEquipa);
    }

    @PostMapping("/{equipaId}/adicionar-jogador/{jogadorId}")
    public void adicionarJogador(@PathVariable UUID equipaId, @PathVariable UUID jogadorId) {
        teamService.adicionarJogador(equipaId, jogadorId);
    }

    @DeleteMapping("/{equipaId}/remover-jogador/{jogadorId}")
    public void removerJogador(@PathVariable UUID equipaId, @PathVariable UUID jogadorId) {
        teamService.removerJogador(equipaId, jogadorId);
    }


}
