package com.example.LigaEsports.service;

import com.example.LigaEsports.DTO.EstatisticaJogadorDTO;
import com.example.LigaEsports.domain.*;
import com.example.LigaEsports.repository.TeamRepository;
import com.example.LigaEsports.repository.UtilizadorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerServiceTest {

    private UtilizadorRepository utilizadorRepo;
    private TeamRepository teamRepository;
    private PlayerService service;

    @BeforeEach
    void setup() {
        utilizadorRepo = mock(UtilizadorRepository.class);
        teamRepository = mock(TeamRepository.class);
        service = new PlayerService(utilizadorRepo, teamRepository);
    }

    @Test
    void atualizarEstatisticasDoJogador_FPS() {
        PlayerFPS fps = mock(PlayerFPS.class);
        when(fps.getNum_games()).thenReturn(3);
        UUID id = UUID.randomUUID();
        when(fps.getId()).thenReturn(id);
        when(utilizadorRepo.getPlayerById(id)).thenReturn(fps);

        EstatisticaJogadorDTO dto = new EstatisticaJogadorDTO();
        dto.setJogadorId(id);
        dto.setTipoJogo(Game.FPS);
        dto.setPrecisao(87.5);
        dto.setHeadshots(4);

        service.atualizarEstatisticasDoJogador(dto);

        verify(fps).setNum_games(4);
        verify(fps).setPrecisao(87.5);
        verify(fps).setHeadshots(4);
    }

    @Test
    void atualizarEstatisticasDoJogador_MOBA() {
        PlayerMOBA moba = mock(PlayerMOBA.class);
        when(moba.getNum_games()).thenReturn(10);
        UUID id = UUID.randomUUID();
        when(moba.getId()).thenReturn(id);
        when(utilizadorRepo.getPlayerById(id)).thenReturn(moba);

        EstatisticaJogadorDTO dto = new EstatisticaJogadorDTO();
        dto.setJogadorId(id);
        dto.setTipoJogo(Game.MOBA);
        dto.setKills(7);
        dto.setDeaths(3);
        dto.setAssists(5);
        dto.setPersonagemPrincipal("Mage");

        service.atualizarEstatisticasDoJogador(dto);

        verify(moba).setNum_games(11);
        verify(moba).setNum_kills(7);
        verify(moba).setNum_deaths(3);
        verify(moba).setNum_assists(5);
        verify(moba).setPersonagem("Mage");
    }

    @Test
    void atualizarEstatisticasDoJogador_EFOOTBALL() {
        PlayerEFootball ef = mock(PlayerEFootball.class);
        when(ef.getNum_games()).thenReturn(1);
        UUID id = UUID.randomUUID();
        when(ef.getId()).thenReturn(id);
        when(utilizadorRepo.getPlayerById(id)).thenReturn(ef);

        EstatisticaJogadorDTO dto = new EstatisticaJogadorDTO();
        dto.setJogadorId(id);
        dto.setTipoJogo(Game.EFOOTBALL);
        dto.setPosicao(null);
        dto.setGolos(2);
        dto.setAssistencias(1);

        service.atualizarEstatisticasDoJogador(dto);

        verify(ef).setNum_games(2);
        verify(ef).setPosition(null);
        verify(ef).setNum_goals(2);
        verify(ef).setNum_assists(1);
    }

    @Test
    void getEstatisticas_shouldAppendToStringOutput() {
        UUID id = UUID.randomUUID();
        Player player = mock(Player.class);
        when(utilizadorRepo.getPlayerById(id)).thenReturn(player);
        List<String> stats = new ArrayList<>();
        when(player.getStatistics()).thenReturn(stats);
        when(player.toString()).thenReturn("PLAYER-TEXT");

        List<String> result = service.getEstatisticas(id);
        assertEquals(1, result.size());
        assertEquals("PLAYER-TEXT", result.get(0));
    }

    @Test
    void getEquipaDoJogador_shouldFindTeamContainingPlayer() {
        UUID playerId = UUID.randomUUID();
        Player p = mock(Player.class);
        when(p.getId()).thenReturn(playerId);

        Team teamWithPlayer = mock(Team.class);
        when(teamWithPlayer.getPlayers()).thenReturn(List.of(p));
        Team otherTeam = mock(Team.class);
        when(otherTeam.getPlayers()).thenReturn(List.of());
        when(teamRepository.listarTodas()).thenReturn(List.of(otherTeam, teamWithPlayer));

        var found = service.getEquipaDoJogador(playerId);
        assertTrue(found.isPresent());
        assertEquals(teamWithPlayer, found.get());
    }
}
