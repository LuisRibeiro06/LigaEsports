package com.example.controller;

import com.example.domain.Player;
import com.example.domain.Team;
import com.example.service.TeamService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/treinadores/{treinadorId}/team")
@CrossOrigin(origins = "http://localhost:3000")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping
    public void criarEquipa(@PathVariable Double idTreinador, @RequestBody Team equipa) {
        equipa.setId(idTreinador);
        teamService.salvar(equipa);
    }

    @PutMapping("/{teamId}")
    public void editarEquipa(@PathVariable Double id, @RequestBody Team equipa) {
        equipa.setId(id);
        teamService.salvar(equipa);
    }

    @PostMapping("/{equipaId}/adicionar-jogador")
    public void adicionarJogador(@PathVariable Double id, @RequestBody Player jogador) {
        teamService.adicionarJogador(id, jogador);
    }

    @DeleteMapping("/{equipaId}/remover-jogador/{jogadorId}")
    public void removerJogador(@PathVariable Double id, @RequestBody Player jogador) {
        teamService.removerJogador(id, jogador);
    }


}
