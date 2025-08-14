package com.example.LigaEsports.service;

import com.example.LigaEsports.domain.Administrador;
import com.example.LigaEsports.domain.Utilizador;
import com.example.LigaEsports.repository.UtilizadorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UtilizadorServiceTest {

    private UtilizadorRepository repo;
    private UtilizadorService service;

    @BeforeEach
    void setUp() {
        repo = mock(UtilizadorRepository.class);
        service = new UtilizadorService(repo);
    }

    @Test
    void init_shouldSeedAdmin_whenListIsNull() {
        when(repo.listarTodos()).thenReturn(null);

        service.init();

        ArgumentCaptor<Utilizador> captor = ArgumentCaptor.forClass(Utilizador.class);
        verify(repo, times(1)).salvar(captor.capture());
        Utilizador saved = captor.getValue();
        assertTrue(saved instanceof Administrador);
    }

    @Test
    void init_shouldSeedAdmin_whenListIsEmpty() {
        when(repo.listarTodos()).thenReturn(Collections.emptyList());

        service.init();

        verify(repo, times(1)).salvar(any(Utilizador.class));
    }

    @Test
    void salvar_and_listarTodos_delegateToRepository() {
        Utilizador u = mock(Utilizador.class);
        service.salvar(u);
        verify(repo).salvar(u);

        List<Utilizador> expected = List.of(u);
        when(repo.listarTodos()).thenReturn(expected);
        assertEquals(expected, service.listarTodos());
        verify(repo, atLeastOnce()).listarTodos();
    }

    @Test
    void remover_delegatesToRepository() {
        service.remover(java.util.UUID.randomUUID());
        verify(repo).remover(any());
    }

    @Test
    void getById_deveRetornarOpcionalVazio_quandoUtilizadorNaoEncontrado() {
        // Given
        UUID idExistente = UUID.randomUUID();
        Utilizador utilizadorExistente = mock(Utilizador.class);
        when(utilizadorExistente.getId()).thenReturn(idExistente);
        when(repo.listarTodos()).thenReturn(List.of(utilizadorExistente));

        UUID idNaoExistente = UUID.randomUUID();

        // When
        Optional<Utilizador> resultado = service.getById(idNaoExistente);

        // Then
        assertTrue(resultado.isEmpty(), "Deveria retornar um Optional vazio para um ID que não existe.");
    }
}
