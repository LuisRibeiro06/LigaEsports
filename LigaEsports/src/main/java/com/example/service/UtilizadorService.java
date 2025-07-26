package com.example.service;

import com.example.domain.Utilizador;
import com.example.repository.UtilizadorRepository;

import java.util.List;
import java.util.Optional;

public class UtilizadorService {

    private final UtilizadorRepository repo = new UtilizadorRepository();


    public void salvar(Utilizador u) {
        // Atualiza se já existir
        remover(u.getId());
        repo.salvar(u);
    }

    public void remover(Double id) {
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
