package com.example.LigaEsports.controller;

import com.example.LigaEsports.DTO.TorneioDTO;
import com.example.LigaEsports.Mapper.TorneioMapper;
import com.example.LigaEsports.domain.Partida;
import com.example.LigaEsports.service.TorneioService;
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

    @PostMapping("/{torneioId}/inscrever")
    public void inscreverEquipa(@PathVariable UUID torneioId, @RequestBody UUID equipaId) {
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
