package com.example.LigaEsports.controller;

import com.example.LigaEsports.DTO.LoginDTO;
import com.example.LigaEsports.Mapper.LoginMapper;
import com.example.LigaEsports.domain.Utilizador;
import com.example.LigaEsports.service.UtilizadorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
//@CrossOrigin(origins = "http://localhost:5176")
public class AuthController {

    private final UtilizadorService utilizadorService;

    public AuthController(UtilizadorService utilizadorService) {
        this.utilizadorService = utilizadorService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        for (Utilizador u : utilizadorService.listarTodos()) {
            if (
                    u.getUsername().equals(loginDTO.getUsername()) &&
                            u.getPassword().equals(loginDTO.getPassword())  // método abstrato em Utilizador
            ) {
                return ResponseEntity.ok(LoginMapper.toDTO(u));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
    }
}
