package com.example.LigaEsports.controller;

import com.example.LigaEsports.DTO.ResultadoDTO;
import com.example.LigaEsports.DTO.TeamRankingDTO;
import com.example.LigaEsports.DTO.TorneioDTO;
import com.example.LigaEsports.Mapper.TorneioMapper;
import com.example.LigaEsports.domain.Partida;
import com.example.LigaEsports.domain.Player;
import com.example.LigaEsports.domain.Torneio;
import com.example.LigaEsports.service.TorneioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/torneios")
//@CrossOrigin(origins = "http://localhost:5176")
public class TorneioController {

    private final TorneioService torneioService;

    public TorneioController(TorneioService torneioService) {
        this.torneioService = torneioService;
    }

    @GetMapping("/torneiosDisponiveis/{equipaId}")
    public List<TorneioDTO> listarTorneiosDisponiveis(@PathVariable UUID equipaId) {
        return torneioService.listarTorneiosDisponiveis(equipaId).stream().map(TorneioMapper::toDTO).toList();
    }

    @PostMapping("/{torneioId}/inscrever")
    public void inscreverEquipa(@PathVariable UUID torneioId, @RequestBody UUID equipaId) {
        torneioService.inscreverEquipa(torneioId, equipaId);
    }

    @GetMapping("/equipa/{equipaId}/torneios")
    public List<TorneioDTO> listarTorneiosDaEquipa(@PathVariable UUID equipaId) {
        return torneioService.getTorneiosDaEquipa(equipaId)
                .stream().map(TorneioMapper::toDTO).toList();
    }

    @GetMapping("/equipa/{equipaId}/partidas")
    public List<Partida> listarPartidasDaEquipa(@PathVariable UUID equipaId) {
        return torneioService.getPartidasDaEquipa(equipaId);
    }

    @GetMapping("/{torneioId}/classificacao")
    public List<TeamRankingDTO> getClassificacao(@PathVariable UUID torneioId) {
        return torneioService.getClassificacao(torneioId);
    }

    @GetMapping("/{torneioId}/partidas")
    public List<Partida> listarPartidas(@PathVariable UUID torneioId) {
        return torneioService.getPartidasDoTorneio(torneioId);
    }

    @GetMapping("/{torneioId}/equipas/{equipaId}/jogadores-validos")
    public List<Player> listarJogadoresValidos(@PathVariable UUID torneioId, @PathVariable UUID equipaId) {
        return torneioService.listarJogadoresValidosParaTorneio(torneioId, equipaId);
    }

}
