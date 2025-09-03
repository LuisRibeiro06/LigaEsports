package com.example.LigaEsports.controller;

import com.example.LigaEsports.DTO.PartidaDTO;
import com.example.LigaEsports.DTO.ResultadoDTO;
import com.example.LigaEsports.DTO.TeamRankingDTO;
import com.example.LigaEsports.DTO.TorneioDTO;
import com.example.LigaEsports.Mapper.PartidaMapper;
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
    public ResponseEntity<List<TorneioDTO>> listarTorneiosDisponiveis(@PathVariable UUID equipaId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(torneioService.listarTorneiosDisponiveis(equipaId).stream().map(TorneioMapper::toDTO).toList());
    }

    @PostMapping("/{torneioId}/inscrever")
    public ResponseEntity<Void> inscreverEquipa(@PathVariable UUID torneioId, @RequestBody UUID equipaId) {
        torneioService.inscreverEquipa(torneioId, equipaId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/equipa/{equipaId}/torneios")
    public ResponseEntity<List<TorneioDTO>> listarTorneiosDaEquipa(@PathVariable UUID equipaId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(torneioService.getTorneiosDaEquipa(equipaId)
                .stream().map(TorneioMapper::toDTO).toList());
    }

    @GetMapping("/equipa/{equipaId}/partidas")
    public ResponseEntity<List<PartidaDTO>> listarPartidasDaEquipa(@PathVariable UUID equipaId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(torneioService.getPartidasDaEquipa(equipaId).stream().map(PartidaMapper::toDto).toList());
    }

    @GetMapping("/{torneioId}/classificacao")
    public ResponseEntity<List<TeamRankingDTO>> getClassificacao(@PathVariable UUID torneioId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(torneioService.getClassificacao(torneioId));
    }

    @GetMapping("/{torneioId}/partidas")
    public ResponseEntity<List<PartidaDTO>> listarPartidas(@PathVariable UUID torneioId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(torneioService.getPartidasDoTorneio(torneioId).stream().map(PartidaMapper::toDto).toList());
    }

    @GetMapping("/{torneioId}/equipas/{equipaId}/jogadores-validos")
    public ResponseEntity<List<Player>> listarJogadoresValidos(@PathVariable UUID torneioId, @PathVariable UUID equipaId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(torneioService.listarJogadoresValidosParaTorneio(torneioId, equipaId));
    }

}
