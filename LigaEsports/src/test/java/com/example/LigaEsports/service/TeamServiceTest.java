package com.example.LigaEsports.service;

import com.example.LigaEsports.domain.Partida;
import com.example.LigaEsports.domain.Player;
import com.example.LigaEsports.domain.Team;
import com.example.LigaEsports.repository.TeamRepository;
import com.example.LigaEsports.repository.UtilizadorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TeamServiceTest {

    private TeamRepository teamRepo;
    private UtilizadorRepository utilizadorRepo;
    private TeamService service;

    @BeforeEach
    void setup() {
        teamRepo = mock(TeamRepository.class);
        utilizadorRepo = mock(UtilizadorRepository.class);
        service = new TeamService(teamRepo, utilizadorRepo);
    }

    @Test
    void atualizarEstatisticasEquipa_shouldIncrementWinLoss_andPersist() {
        // Arrange
        Team team1 = mock(Team.class);
        Team team2 = mock(Team.class);
        when(team1.getVitorias()).thenReturn(0);
        when(team2.getDerrotas()).thenReturn(0);

        Partida partida = mock(Partida.class);
        when(partida.getTeam1()).thenReturn(team1);
        when(partida.getTeam2()).thenReturn(team2);
        when(partida.getResultado()).thenReturn("2-1");

        // Act
        service.atualizarEstatisticasEquipa(partida);

        // Assert
        verify(team1).setVitorias(1);
        verify(team2).setDerrotas(1);
        verify(teamRepo).salvar(team1);
        verify(teamRepo).salvar(team2);
    }

    @Test
    void atualizarEstatisticasEquipa_draw_shouldNotChangeCounters_butPersist() {
        Team team1 = mock(Team.class);
        Team team2 = mock(Team.class);
        Partida partida = mock(Partida.class);
        when(partida.getTeam1()).thenReturn(team1);
        when(partida.getTeam2()).thenReturn(team2);
        when(partida.getResultado()).thenReturn("1-1");

        service.atualizarEstatisticasEquipa(partida);

        verify(team1, never()).setVitorias(anyInt());
        verify(team1, never()).setDerrotas(anyInt());
        verify(team2, never()).setVitorias(anyInt());
        verify(team2, never()).setDerrotas(anyInt());
        verify(teamRepo).salvar(team1);
        verify(teamRepo).salvar(team2);
    }

    @Test
    void reverterEstatisticas_shouldDecrementPreviousWinLoss() {
        Team team1 = mock(Team.class);
        Team team2 = mock(Team.class);
        when(team1.getVitorias()).thenReturn(2);
        when(team2.getDerrotas()).thenReturn(5);
        Partida partida = mock(Partida.class);
        when(partida.getTeam1()).thenReturn(team1);
        when(partida.getTeam2()).thenReturn(team2);
        when(partida.getResultado()).thenReturn("3-2");

        service.reverterEstatisticas(partida);

        verify(team1).setVitorias(1);
        verify(team2).setDerrotas(4);
    }

    @Test
    void listarJogadoresSemEquipa_shouldReturnOnlyUnassignedPlayers() {
        // Teams with players
        Player p1 = mock(Player.class);
        Player p2 = mock(Player.class);
        Team t1 = mock(Team.class);
        when(t1.getPlayers()).thenReturn(List.of(p1));
        Team t2 = mock(Team.class);
        when(t2.getPlayers()).thenReturn(List.of(p2));
        when(teamRepo.listarTodas()).thenReturn(List.of(t1, t2));

        // All players in system include p1, p2, and p3 (unassigned)
        Player p3 = mock(Player.class);
        when(utilizadorRepo.getAllPlayers()).thenReturn(List.of(p1, p2, p3));

        var result = service.listarJogadoresSemEquipa();
        assertEquals(1, result.size());
        assertTrue(result.contains(p3));
    }

    @Test
    void criarEquipa_whenTreinadorNotFound_shouldThrow() {
        UUID treinadorId = UUID.randomUUID();
        when(utilizadorRepo.getTreinadorById(treinadorId)).thenThrow(new RuntimeException("not found"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.criarEquipa(treinadorId, "Nome"));
        assertEquals("Treinador não encontrado", ex.getMessage());
    }

    @Test
    void editarNomeEquipa_whenTeamNotFound_shouldThrow() {
        UUID equipaId = UUID.randomUUID();
        when(teamRepo.getById(equipaId)).thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.editarNomeEquipa(equipaId, "Novo"));
        assertEquals("Equipa não encontrada", ex.getMessage());
    }

    @Test
    void adicionarJogador_whenPlayerNotFound_shouldThrow() {
        UUID equipaId = UUID.randomUUID();
        UUID jogadorId = UUID.randomUUID();
        when(utilizadorRepo.getPlayerById(jogadorId)).thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.adicionarJogador(equipaId, jogadorId));
        assertEquals("Jogador nao encontrado", ex.getMessage());
        verify(teamRepo, never()).adicionarJogador(any(), any());
    }
}
