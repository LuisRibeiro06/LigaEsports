package com.example.LigaEsports.service;

import com.example.LigaEsports.DTO.EstatisticaJogadorDTO;
import com.example.LigaEsports.DTO.PartidaDTO;
import com.example.LigaEsports.DTO.ResultadoDTO;
import com.example.LigaEsports.domain.*;
import com.example.LigaEsports.repository.TeamRepository;
import com.example.LigaEsports.repository.TorneioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TorneioServiceTest {

    private TorneioRepository torneioRepo;
    private TeamRepository teamRepo;
    private TeamService teamService;
    private PlayerService playerService;
    private TorneioService service;

    @BeforeEach
    void setup() {
        torneioRepo = mock(TorneioRepository.class);
        teamRepo = mock(TeamRepository.class);
        teamService = mock(TeamService.class);
        playerService = mock(PlayerService.class);
        service = new TorneioService(torneioRepo, teamRepo, teamService, playerService);
    }

    @Test
    void inscreverEquipa_shouldThrow_whenTorneioNotFound() {
        UUID torneioId = UUID.randomUUID();
        when(torneioRepo.getById(torneioId)).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> service.inscreverEquipa(torneioId, UUID.randomUUID()));
    }

    @Test
    void inscreverEquipa_shouldThrow_whenEquipaNotFound() {
        UUID torneioId = UUID.randomUUID();
        when(torneioRepo.getById(torneioId)).thenReturn(mock(Torneio.class));
        when(teamRepo.getById(any())).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> service.inscreverEquipa(torneioId, UUID.randomUUID()));
    }

    @Test
    void inscreverEquipa_shouldThrow_whenTeamHasNoPlayers() {
        UUID torneioId = UUID.randomUUID();
        UUID equipaId = UUID.randomUUID();
        Torneio t = mock(Torneio.class);
        when(torneioRepo.getById(torneioId)).thenReturn(t);
        Team team = mock(Team.class);
        when(teamRepo.getById(equipaId)).thenReturn(team);
        when(team.getPlayers()).thenReturn(Collections.emptyList());

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> service.inscreverEquipa(torneioId, equipaId));
        assertEquals("Equipa sem jogadores!", ex.getMessage());
    }

    @Test
    void inscreverEquipa_shouldThrow_whenIncompatibleGame() {
        UUID torneioId = UUID.randomUUID();
        UUID equipaId = UUID.randomUUID();
        Torneio t = mock(Torneio.class);
        when(t.getGame()).thenReturn(mock(Game.class));
        when(t.getTeams()).thenReturn(new ArrayList<>());
        when(torneioRepo.getById(torneioId)).thenReturn(t);

        Team team = mock(Team.class);
        Player player = mock(Player.class);
        when(player.getGame()).thenReturn(mock(Game.class)); // different instance -> not equals
        when(team.getPlayers()).thenReturn(List.of(player));
        when(teamRepo.getById(equipaId)).thenReturn(team);

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> service.inscreverEquipa(torneioId, equipaId));
        assertTrue(ex.getMessage().contains("compatível"));
    }

    @Test
    void inscreverEquipa_shouldThrow_whenAlreadyRegistered() {
        UUID torneioId = UUID.randomUUID();
        UUID equipaId = UUID.randomUUID();
        Torneio t = mock(Torneio.class);
        Team existing = mock(Team.class);
        when(existing.getId()).thenReturn(equipaId);
        when(t.getTeams()).thenReturn(new ArrayList<>(List.of(existing)));
        when(torneioRepo.getById(torneioId)).thenReturn(t);

        Team team = mock(Team.class);
        when(team.getPlayers()).thenReturn(List.of(mock(Player.class)));
        when(teamRepo.getById(equipaId)).thenReturn(team);
        Player existingPlayer = mock(Player.class);
        when(existingPlayer.getGame()).thenReturn(mock(Game.class));
        Game existingPlayerGame = existingPlayer.getGame();
        t.setGame(existingPlayerGame);
        when(team.getPlayers()).thenReturn(List.of(existingPlayer));
        when(t.getGame()).thenReturn(existingPlayerGame);

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> service.inscreverEquipa(torneioId, equipaId));
        assertEquals("Equipa já inscrita no torneio!", ex.getMessage());
    }

    @Test
    void inscreverEquipa_success_shouldPersist() {
        UUID torneioId = UUID.randomUUID();
        UUID equipaId = UUID.randomUUID();



        // Arrange
        Torneio t = mock(Torneio.class);
        Game game = mock(Game.class);
        when(t.getTeams()).thenReturn(new ArrayList<>());
        when(t.getGame()).thenReturn(game);
        when(torneioRepo.getById(torneioId)).thenReturn(t);

        Team team = mock(Team.class);
        Player comp = mock(Player.class);
        Game gamePlayer = comp.getGame();
        when(gamePlayer).thenReturn(game); // Garante a compatibilidade do jogo
        when(team.getPlayers()).thenReturn(List.of(comp));
        when(teamRepo.getById(equipaId)).thenReturn(team);

        // Act
        service.inscreverEquipa(torneioId, equipaId);

        // Assert
        verify(t).addTeam(team);
        verify(torneioRepo).salvar(t);
    }

    @Test
    void agendarPartida_shouldValidateTeamsAndAddPartida() {
        UUID torneioId = UUID.randomUUID();
        Torneio torneio = mock(Torneio.class);
        List<Team> teams = new ArrayList<>();
        Team team1 = mock(Team.class);
        Team team2 = mock(Team.class);
        teams.add(team1);
        teams.add(team2);
        when(torneio.getTeams()).thenReturn(teams);
        List<Partida> partidas = new ArrayList<>();
        when(torneio.getPartidas()).thenReturn(partidas);
        when(torneioRepo.getById(torneioId)).thenReturn(torneio);

        UUID team1Id = UUID.randomUUID();
        UUID team2Id = UUID.randomUUID();
        when(teamRepo.getById(team1Id)).thenReturn(team1);
        when(teamRepo.getById(team2Id)).thenReturn(team2);

        // players mapping
        Player p1 = mock(Player.class);
        Player p2 = mock(Player.class);
        UUID p1Id = UUID.randomUUID();
        UUID p2Id = UUID.randomUUID();
        when(p1.getId()).thenReturn(p1Id);
        when(p2.getId()).thenReturn(p2Id);
        when(team1.getPlayers()).thenReturn(List.of(p1));
        when(team2.getPlayers()).thenReturn(List.of(p2));

        PartidaDTO dto = mock(PartidaDTO.class);
        when(dto.getEquipe1Id()).thenReturn(team1Id);
        when(dto.getEquipe2Id()).thenReturn(team2Id);
        when(dto.getJogadoresEquipe1()).thenReturn(List.of(p1Id));
        when(dto.getJogadoresEquipe2()).thenReturn(List.of(p2Id));
        when(dto.getData()).thenReturn(new java.util.Date());

        service.agendarPartida(torneioId, dto);

        assertEquals(1, partidas.size());
        Partida created = partidas.get(0);
        assertNotNull(created);
        assertEquals(1, created.getPlayersTeam1().size());
        assertEquals(1, created.getPlayersTeam2().size());
    }

    @Test
    void alterarResultado_shouldRevertApplyAndPersist() {
        UUID torneioId = UUID.randomUUID();
        Torneio torneio = mock(Torneio.class);
        List<Partida> partidas = new ArrayList<>();
        Partida partida = mock(Partida.class);
        when(partida.getId()).thenReturn(UUID.randomUUID());
        Team t1 = mock(Team.class);
        Team t2 = mock(Team.class);
        when(partida.getTeam1()).thenReturn(t1);
        when(partida.getTeam2()).thenReturn(t2);
        when(partida.getResultado()).thenReturn("1-0");
        partidas.add(partida);
        when(torneio.getPartidas()).thenReturn(partidas);
        when(torneioRepo.getById(torneioId)).thenReturn(torneio);

        ResultadoDTO dto = new ResultadoDTO();
        dto.setPartidaId(partida.getId());
        dto.setResultado("0-2");
        dto.setEstatisticasJogadores(List.of(new EstatisticaJogadorDTO()));

        service.alterarResultado(torneioId, dto);

        verify(teamService).reverterEstatisticas(partida);
        verify(teamService).atualizarEstatisticasEquipa(partida);
        verify(playerService, atLeastOnce()).atualizarEstatisticasDoJogador(any());
        verify(torneioRepo).salvar(torneio);
        verify(teamRepo).salvar(t1);
        verify(teamRepo).salvar(t2);
    }

    @Test
    void getClassificacao_shouldComputePointsAndOrder() {
        UUID torneioId = UUID.randomUUID();
        Torneio torneio = mock(Torneio.class);
        Team a = mock(Team.class); when(a.getId()).thenReturn(UUID.randomUUID()); when(a.getNome()).thenReturn("A");
        Team b = mock(Team.class); when(b.getId()).thenReturn(UUID.randomUUID()); when(b.getNome()).thenReturn("B");
        when(torneio.getTeams()).thenReturn(List.of(a,b));

        Partida p1 = mock(Partida.class);
        when(p1.getTeam1()).thenReturn(a);
        when(p1.getTeam2()).thenReturn(b);
        when(p1.getResultado()).thenReturn("2-1");
        Partida p2 = mock(Partida.class);
        when(p2.getTeam1()).thenReturn(a);
        when(p2.getTeam2()).thenReturn(b);
        when(p2.getResultado()).thenReturn("1-1");
        when(torneio.getPartidas()).thenReturn(List.of(p1, p2));

        when(torneioRepo.getById(torneioId)).thenReturn(torneio);

        var ranking = service.getClassificacao(torneioId);
        assertEquals(2, ranking.size());
        assertEquals("A", ranking.get(0).getNome()); // A: win + draw -> 4 pts
        assertEquals(4, ranking.get(0).getPontos());
        assertEquals("B", ranking.get(1).getNome());
    }
}