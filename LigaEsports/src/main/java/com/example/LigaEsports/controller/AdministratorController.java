package com.example.LigaEsports.controller;

import com.example.LigaEsports.DTO.PartidaDTO;
import com.example.LigaEsports.DTO.ResultadoDTO;
import com.example.LigaEsports.DTO.TorneioDTO;
import com.example.LigaEsports.DTO.UtilizadorDTO;
import com.example.LigaEsports.Mapper.TorneioMapper;
import com.example.LigaEsports.Mapper.UtilizadorMapper;
import com.example.LigaEsports.domain.Partida;
import com.example.LigaEsports.domain.Torneio;
import com.example.LigaEsports.domain.Utilizador;
import com.example.LigaEsports.service.TorneioService;
import com.example.LigaEsports.service.UtilizadorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
//@CrossOrigin(origins = "http://localhost:5176")
public class AdministratorController {

    private final UtilizadorService utilizadorService;
    private final TorneioService torneioService;

    public AdministratorController(UtilizadorService utilizadorService, TorneioService torneioService) {
        this.utilizadorService = utilizadorService;
        this.torneioService = torneioService;
    }

    @GetMapping("/utilizadores")
    public List<Utilizador> listarTodos() {
        return utilizadorService.listarTodos();
    }

    @PostMapping("/utilizadores")
    public void adicionarUtilizador(@RequestBody UtilizadorDTO novo) {
        Utilizador utilizador = UtilizadorMapper.criarUtilizador(novo);
        utilizadorService.salvar(utilizador);
    }

    @DeleteMapping("/utilizadores/{id}")
    public void apagarUtilizador(@PathVariable UUID id) {
        utilizadorService.remover(id);
    }

    @GetMapping("/torneios")
    public List<Torneio> listarTorneios() {
        return torneioService.listarTodos();
    }

    @PostMapping("/torneios")
    public void criarTorneio(@RequestBody Torneio torneio) {
        torneioService.salvar(torneio);
    }

    @PutMapping("/torneios/{id}")
    public void editarTorneio(@PathVariable UUID id, @RequestBody Torneio atualizado) {
        atualizado.setId(id);
        torneioService.salvar(atualizado);
    }

    @DeleteMapping("/torneios/{id}")
    public void apagarTorneio(@PathVariable UUID id) {
        torneioService.apagar(id);
    }

    @PostMapping("/torneios/{torneioId}/partidas")
    public void agendarPartida(@PathVariable UUID torneioId, @RequestBody PartidaDTO partida) {
        torneioService.agendarPartida(torneioId, partida);
    }


    @PutMapping("/partidas/{id}")
    public void alterarResultado(@PathVariable UUID id, @RequestBody ResultadoDTO atualizado) {
        torneioService.alterarResultado(id, atualizado);
    }

    @GetMapping("/torneios/{id}/estatisticas")
    public String verEstatisticas(@PathVariable UUID id) {
        return torneioService.verEstatisticasTorneio(id);
    }

}
