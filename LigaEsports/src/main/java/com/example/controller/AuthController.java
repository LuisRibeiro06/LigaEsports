package com.example.controller;

import com.example.DTO.LoginDTO;
import com.example.DTO.LoginResponseDTO;
import com.example.Mapper.LoginMapper;
import com.example.domain.Utilizador;
import com.example.service.UtilizadorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
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
                            u.getPassword().equals(loginDTO.getPassword()) &&
                            u.getRole().equals(loginDTO.getRole()) // método abstrato em Utilizador
            ) {
                return ResponseEntity.ok(LoginMapper.toDTO(u));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
    }
}
