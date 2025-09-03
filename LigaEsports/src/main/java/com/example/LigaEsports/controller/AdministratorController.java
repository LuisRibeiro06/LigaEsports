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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<List<UtilizadorDTO>> listarTodos() {
        List<Utilizador> utilizadores = utilizadorService.listarTodos();
        List<UtilizadorDTO> utilizadoresDTO = new ArrayList<>();
        for (Utilizador u : utilizadores) {
            utilizadoresDTO.add(UtilizadorMapper.toDTO(u));
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(utilizadoresDTO);
    }

    @PostMapping("/utilizadores")
    public ResponseEntity<UtilizadorDTO> adicionarUtilizador(@RequestBody UtilizadorDTO novo) {
        Utilizador utilizador = UtilizadorMapper.criarUtilizador(novo);
        utilizadorService.salvar(utilizador);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UtilizadorMapper.toDTO(utilizador));
    }


    @DeleteMapping("/utilizadores/{id}")
    public ResponseEntity<Void> apagarUtilizador(@PathVariable UUID id) {
        utilizadorService.remover(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/torneios")
    public ResponseEntity<List<TorneioDTO>> listarTorneios() {
        List<Torneio> torneios = torneioService.listarTodos();
        List<TorneioDTO> torneiosDTO = new ArrayList<>();
        for (Torneio t : torneios) {
            torneiosDTO.add(TorneioMapper.toDTO(t));
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(torneiosDTO);
    }

    @PostMapping("/torneios")
    public ResponseEntity<Void> criarTorneio(@RequestBody Torneio torneio) {
        torneioService.salvar(torneio);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/torneios/{id}")
    public ResponseEntity<Void> editarTorneio(@PathVariable UUID id, @RequestBody Torneio atualizado) {
        atualizado.setId(id);
        torneioService.salvar(atualizado);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/torneios/{id}")
    public ResponseEntity<Void> apagarTorneio(@PathVariable UUID id) {
        torneioService.apagar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/torneios/{torneioId}/partidas")
    public ResponseEntity<Void> agendarPartida(@PathVariable UUID torneioId, @RequestBody PartidaDTO partida) {
        torneioService.agendarPartida(torneioId, partida);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PutMapping("/partidas/{id}")
    public ResponseEntity<Void> alterarResultado(@PathVariable UUID id, @RequestBody ResultadoDTO atualizado) {
        torneioService.alterarResultado(id, atualizado);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/torneios/{id}/estatisticas")
    public ResponseEntity<String> verEstatisticas(@PathVariable UUID id) {
        String estatisticas = torneioService.verEstatisticasTorneio(id);
        return ResponseEntity.status(HttpStatus.OK).body(estatisticas);
    }

}
