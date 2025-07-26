package com.example.controller;

import com.example.domain.Partida;
import com.example.domain.Torneio;
import com.example.domain.Utilizador;
import com.example.service.TorneioService;
import com.example.service.UtilizadorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdministratorController {

  private final UtilizadorService utilizadorService;
  private final TorneioService torneioService;

  public AdministratorController(UtilizadorService utilizadorService, TorneioService torneioService) {
    this.utilizadorService = utilizadorService;
    this.torneioService = torneioService;
  }


    @PostMapping("/utilizadores")
    public void adicionarUtilizador(@RequestBody Utilizador novo) {
        utilizadorService.salvar(novo);
    }

    @DeleteMapping("/utilizadores/{id}")
    public void apagarUtilizador(@PathVariable Double id) {
        utilizadorService.remover(id);
    }

    @PostMapping("/torneios")
    public void criarTorneio(@RequestBody Torneio torneio) {
        torneioService.salvar(torneio);
    }

    @PutMapping("/torneios/{id}")
    public void editarTorneio(@PathVariable Double id, @RequestBody Torneio atualizado) {
        atualizado.setId(id);
        torneioService.salvar(atualizado);
    }

    @DeleteMapping("/torneios/{id}")
    public void apagarTorneio(@PathVariable Double id) {
        torneioService.apagar(id);
    }

    @PostMapping("/torneios/{torneioId}/partidas")
    public void agendarPartida(@PathVariable Double torneioId, @RequestBody Partida partida) {
        torneioService.agendarPartida(torneioId, partida);
    }

    @PutMapping("/partidas/{id}")
    public void alterarResultado(@PathVariable Double id, @RequestBody Partida atualizado) {
        torneioService.alterarResultado(id, atualizado);
    }

    @GetMapping("/torneios/{id}/estatisticas")
    public String verEstatisticas(@PathVariable Double id) {
        return torneioService.verEstatisticasTorneio(id);
    }

}
