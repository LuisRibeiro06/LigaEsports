package com.example.LigaEsports.service;

import com.example.LigaEsports.DTO.EstatisticaJogadorDTO;
import com.example.LigaEsports.DTO.PartidaDTO;
import com.example.LigaEsports.DTO.ResultadoDTO;
import com.example.LigaEsports.DTO.TeamRankingDTO;
import com.example.LigaEsports.domain.*;
import com.example.LigaEsports.repository.TeamRepository;
import com.example.LigaEsports.repository.TorneioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TorneioService {

    private final TorneioRepository repo;
    private final TeamRepository teamRepo;
    private final TeamService teamService;
    private final PlayerService playerService;

    @Autowired
    public TorneioService(TorneioRepository repo, TeamRepository teamRepo, TeamService teamService, PlayerService playerService) {
        this.repo = repo;
        this.teamRepo = teamRepo;
        this.teamService = teamService;
        this.playerService = playerService;
    }

    public void inscreverEquipa(UUID torneioId, UUID equipaId) {
        Torneio torneio = repo.getById(torneioId);
        if (torneio == null) {
            throw new IllegalArgumentException("Torneio não encontrado");
        }

        Team equipa = teamRepo.getById(equipaId);
        if (equipa == null) {
            throw new IllegalArgumentException("Equipa não encontrada");
        }

        if (equipa.getPlayers().isEmpty() || equipa.getPlayers()==null) {
            throw new IllegalStateException("Equipa sem jogadores!");
        }

        // Verificação extra se quiser aplicar restrições de jogo:
        boolean jogadorCompatible = equipa.getPlayers().stream()
                .anyMatch(j -> j.getGame().equals(torneio.getGame()));
        if (!jogadorCompatible) {
            throw new IllegalStateException("A equipa não tem nenhum jogador compatível com o jogo do torneio.");
        }

        if (torneio.getTeams().stream().anyMatch(e -> e.getId().equals(equipaId))) {
            throw new IllegalStateException("Equipa já inscrita no torneio!");
        }

        torneio.addTeam(equipa);
        repo.salvar(torneio);
    }

    public List<Torneio> listarTodos() {
        return repo.listarTodas();
    }

    public List<Player> listarJogadoresValidosParaTorneio(UUID torneioId, UUID equipaId) {
        Torneio torneio = repo.getById(torneioId);
        if (torneio == null) {
            throw new IllegalArgumentException("Torneio nao encontrado");
        }

        Team equipa = teamRepo.getById(equipaId);
        if (equipa == null) {
            throw new IllegalArgumentException("Equipa nao encontrada");
        }

        Game torneioGame = torneio.getGame();

        return equipa.getPlayers().stream()
                .filter(player -> player.getGame().equals(torneioGame))
                .collect(Collectors.toList());

    }


    public List<Partida> getPartidasDoTorneio(UUID torneioId) {
        Torneio torneio = repo.getById(torneioId);
        return torneio.getPartidas();
    }

    public List<Torneio> getTorneiosDoJogador(UUID jogadorId) {
        List<Torneio> torneios = listarTodos();
        List<Torneio> torneiosDoJogador = torneios.stream()
                .filter(torneio -> torneio.getTeams().stream()
                        .anyMatch(team -> team.getPlayers().stream()
                                .anyMatch(player -> player.getId().equals(jogadorId))))
                .toList();
        return torneiosDoJogador;
    }

    public List<Torneio> getTorneiosDaEquipa(UUID equipaId) {
        return listarTodos().stream()
                .filter(t -> t.getTeams().stream().anyMatch(e -> e.getId().equals(equipaId)))
                .toList();
    }

    public List<Partida> getPartidasDaEquipa(UUID equipaId) {
        return listarTodos().stream()
                .flatMap(t -> t.getPartidas().stream())
                .filter(p -> p.getTeam1().getId().equals(equipaId) || p.getTeam2().getId().equals(equipaId))
                .toList();
    }

    public void agendarPartida(UUID torneioId, PartidaDTO dto) {
        Team team1 = teamRepo.getById(dto.getEquipe1Id());
        Team team2 = teamRepo.getById(dto.getEquipe2Id());
        Torneio torneio = repo.getById(torneioId);

        if (team1 == null || team2 == null) {
            throw new IllegalArgumentException("Ambas as equipas devem ser válidas.");
        }

        if (torneio == null) {
            throw new IllegalArgumentException("Torneio não encontrado.");
        }

        System.out.println("Torneio ID: " + torneioId);
        System.out.println("Equipas inscritas no torneio:");
        torneio.getTeams().forEach(t -> System.out.println(t.getId()));

        System.out.println("Equipe 1 no pedido: " + team1.getId());
        System.out.println("Equipe 2 no pedido: " + team2.getId());

        if (!torneio.getTeams().contains(team1) || !torneio.getTeams().contains(team2)) {
            throw new IllegalArgumentException("Ambas as equipas devem estar inscritas no torneio.");
        }

        List<Player> playersTeam1 = dto.getJogadoresEquipe1().stream()
                .map(jogadorId -> team1.getPlayers().stream()
                        .filter(p -> p.getId().equals(jogadorId))
                        .findFirst()
                        .orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<Player> playersTeam2 = dto.getJogadoresEquipe2().stream()
                .map(jogadorId -> team2.getPlayers().stream()
                        .filter(p -> p.getId().equals(jogadorId))
                        .findFirst()
                        .orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Partida partida = new Partida(team1, team2, null, dto.getData());
        partida.setPlayersTeam1(playersTeam1);
        partida.setPlayersTeam2(playersTeam2);
        torneio.getPartidas().add(partida);
    }


    public void alterarResultado(UUID torneioId, ResultadoDTO dto) {
        Torneio torneio = repo.getById(torneioId);
        if (torneio == null) {
            throw new IllegalArgumentException("Torneio não encontrado.");
        }

        Partida partida = torneio.getPartidas()
                .stream()
                .filter(p -> p.getId().equals(dto.getPartidaId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Partida não encontrada."));

        // Reverter estatísticas anteriores
        if (partida.getResultado() != null && !partida.getResultado().isBlank()) {
            teamService.reverterEstatisticas(partida);
        }

        // Atualizar resultado
        partida.setResultado(dto.getResultado());

        // Aplicar novas estatísticas às equipas
        teamService.atualizarEstatisticasEquipa(partida);

        // Atualizar estatísticas dos jogadores
        if (dto.getEstatisticasJogadores() != null) {
            for (EstatisticaJogadorDTO est : dto.getEstatisticasJogadores()) {
                playerService.atualizarEstatisticasDoJogador(est); // Este metodo é responsável por tratar EFOOTBALL, MOBA, FPS, etc.
            }
        }

        // Persistir alterações
        salvar(torneio);
        teamRepo.salvar(partida.getTeam1());
        teamRepo.salvar(partida.getTeam2());
    }


    public String verEstatisticasTorneio(UUID id) {
        Torneio t = getById(id);
        if (t == null) return "Torneio não encontrado.";

        StringBuilder sb = new StringBuilder();
        sb.append("📊 Estatísticas do Torneio: ").append(t.getName()).append("\n");
        sb.append("🔸 Número de Equipas: ").append(t.getTeams().size()).append("\n");
        sb.append("🔸 Número de Partidas: ").append(t.getPartidas().size()).append("\n");

        t.getPartidas().forEach(p -> {
            sb.append("   - ").append(p.getTeam1().getNome())
                    .append(" vs ").append(p.getTeam2().getNome())
                    .append(" → Resultado: ").append(p.getResultado()).append("\n");
        });

        return sb.toString();
    }

    public List<TeamRankingDTO> getClassificacao(UUID torneioId) {
         Torneio torneio = repo.getById(torneioId);
         if (torneio == null) {
             throw new IllegalArgumentException("Torneio nao encontrado");
         }
        List<TeamRankingDTO> ranking = new ArrayList<>();

        for (Team team : torneio.getTeams()) {
            int vitorias = 0;
            int derrotas = 0;
            int empates = 0;
            int jogos = 0;

            for (Partida p : torneio.getPartidas()) {
                if (p.getTeam1().getId().equals(team.getId()) || p.getTeam2().getId().equals(team.getId())) {
                    jogos++;
                    String res = p.getResultado(); // ex: "2-1", "1-1", "0-3" ou "pendente"
                    if (res == null || res.isEmpty() || res.equalsIgnoreCase("pendente")) continue;

                    String[] placares = res.split("-");
                    if (placares.length != 2) continue;

                    int score1 = Integer.parseInt(placares[0].trim());
                    int score2 = Integer.parseInt(placares[1].trim());

                    boolean isTeam1 = p.getTeam1().getId().equals(team.getId());

                    if (score1 == score2) {
                        empates++;
                    } else if ((score1 > score2 && isTeam1) || (score2 > score1 && !isTeam1)) {
                        vitorias++;
                    } else {
                        derrotas++;
                    }
                }
            }

            int pontos = vitorias * 3 + empates; // regra comum: 3 pts vitória, 1 empate

            TeamRankingDTO dto = new TeamRankingDTO();
            dto.setTeamId(team.getId());
            dto.setNome(team.getNome());
            dto.setJogos(jogos);
            dto.setVitorias(vitorias);
            dto.setDerrotas(derrotas);
            dto.setEmpates(empates);
            dto.setPontos(pontos);

            ranking.add(dto);
        }

        // Ordena por pontos, depois por vitórias
        ranking.sort((a, b) -> {
            if (b.getPontos() != a.getPontos()) return b.getPontos() - a.getPontos();
            return b.getVitorias() - a.getVitorias();
        });

        return ranking;
    }


    public List<Torneio> listarTorneiosDisponiveis(UUID equipaId) {
        List<Torneio> torneios = repo.listarTodas();
        return torneios.stream()
                .filter(t -> !t.getTeams().stream().anyMatch(e -> e.getId().equals(equipaId)))
                .toList();
    }

    public void salvar(Torneio t) {
        repo.salvar(t);
    }

    public void apagar(UUID id) {
        repo.apagar(id);
    }

    public Torneio getById(UUID id) {
        return repo.getById(id);
    }


}