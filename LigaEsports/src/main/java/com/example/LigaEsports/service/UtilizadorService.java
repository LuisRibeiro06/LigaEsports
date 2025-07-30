package com.example.LigaEsports.service;

import com.example.LigaEsports.domain.Administrador;
import com.example.LigaEsports.domain.Utilizador;
import com.example.LigaEsports.repository.UtilizadorRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UtilizadorService {

    private final UtilizadorRepository repo = new UtilizadorRepository();

    @PostConstruct
    public void init() {
        if (listarTodos().isEmpty()) {
            Administrador admin = new Administrador();
            admin.setId(UUID.randomUUID());
            admin.setNome("Admin Principal");
            admin.setPassword("admin123");
            admin.setUsername("admin");
            admin.setEmail("admin@liga.com");
            salvar(admin);
            System.out.println("Administrador pré-definido criado: admin / admin123");
        }
    }

    public void salvar(Utilizador u) {
        // Atualiza se já existir
        remover(u.getId());
        repo.salvar(u);
    }

    public void remover(UUID id) {
        repo.remover(id);
    }

    public List<Utilizador> listarTodos() {
        return repo.listarTodos();
    }

    public Optional<Utilizador> getById(Double id) {
        return listarTodos().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }
}
