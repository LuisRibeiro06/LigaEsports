package com.example.controller;

import com.example.DTO.TorneioDTO;
import com.example.Mapper.TorneioMapper;
import com.example.domain.Game;
import com.example.domain.Partida;
import com.example.service.TorneioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/torneios")
@CrossOrigin(origins = "http://localhost:3000")
public class TorneioController {

    private final TorneioService torneioService;

    public TorneioController(TorneioService torneioService) {
        this.torneioService = torneioService;
    }

    @PostMapping("/{torneioId}/inscrever")
    public void inscreverEquipa(@PathVariable Double torneioId, @RequestBody Double equipaId) {
        torneioService.inscreverEquipa(torneioId, equipaId);
    }

    @GetMapping("/equipa/{equipaId}/torneios")
    public List<TorneioDTO> listarTorneiosDaEquipa(@PathVariable Double equipaId) {
        return torneioService.getTorneiosDaEquipa(equipaId)
                .stream().map(TorneioMapper::toDTO).toList();
    }

    @GetMapping("/equipa/{equipaId}/partidas")
    public List<Partida> listarPartidasDaEquipa(@PathVariable Double equipaId) {
        return torneioService.getPartidasDaEquipa(equipaId);
    }
}
