package com.example.LigaEsports.service;

import com.example.LigaEsports.domain.*;
import com.example.LigaEsports.repository.TeamRepository;
import com.example.LigaEsports.repository.UtilizadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TeamService {

    private final TeamRepository repo;
    private final UtilizadorRepository utilizadorRepo;

    @Autowired
    public TeamService(TeamRepository repo, UtilizadorRepository utilizadorRepo) {
        this.repo = repo;
        this.utilizadorRepo = utilizadorRepo;
    }
    
    public List<Team> listarTodas() {
        return repo.listarTodas();
    }


    public void apagar(UUID id) {
        repo.apagar(id);
    }

    public List<Player> listarJogadoresSemEquipa() {
        List<Team> teams = listarTodas();

        List<Player> jogadoresComEquipa = teams.stream()
                .flatMap(t -> t.getPlayers().stream())
                .toList();

        // 2. Todos os jogadores do sistema
        List<Player> todosJogadores = utilizadorRepo.getAllPlayers();

        // 3. Filtrar para excluir quem já está em alguma equipa
        return todosJogadores.stream()
                .filter(p -> !jogadoresComEquipa.contains(p)) // não está em equipa nenhuma
                .toList();
    }


    public void atualizarEstatisticasEquipa(Partida partida) {
        Team team1 = partida.getTeam1();
        Team team2 = partida.getTeam2();
        String resultado = partida.getResultado();

        if (resultado == null || resultado.isEmpty() || resultado.equalsIgnoreCase("pendente")) return;

        String[] placares = resultado.split("-");
        if (placares.length != 2) return;

        int score1 = Integer.parseInt(placares[0].trim());
        int score2 = Integer.parseInt(placares[1].trim());


        if (score1 == score2) {
            // empate
        } else if (score1 > score2) {
            team1.setVitorias(team1.getVitorias() + 1);
            team2.setDerrotas(team2.getDerrotas() + 1);
        } else {
            team2.setVitorias(team2.getVitorias() + 1);
            team1.setDerrotas(team1.getDerrotas() + 1);
        }

        repo.salvar(team1);
        repo.salvar(team2);
    }

    //usar para caso seja alterado um resultado já existente
    public void reverterEstatisticas(Partida partida) {
        String resultadoAntigo = partida.getResultado();
        if (resultadoAntigo == null || resultadoAntigo.isEmpty() || resultadoAntigo.equalsIgnoreCase("pendente")) return;

        String[] placares = resultadoAntigo.split("-");
        if (placares.length != 2) return;

        int score1 = Integer.parseInt(placares[0].trim());
        int score2 = Integer.parseInt(placares[1].trim());

        Team team1 = partida.getTeam1();
        Team team2 = partida.getTeam2();

        if (score1 == score2) {
            // empate
        } else if (score1 > score2) {
            team1.setVitorias(team1.getVitorias() - 1);
            team2.setDerrotas(team2.getDerrotas() - 1);
        } else {
            team2.setVitorias(team2.getVitorias() - 1);
            team1.setDerrotas(team1.getDerrotas() - 1);
        }
    }


    public Team criarEquipa(UUID treinadorId, String nomeEquipa) {
        try {
            Treinador treinador = utilizadorRepo.getTreinadorById(treinadorId);
            Team equipa = new Team(nomeEquipa, treinador);
            repo.salvar(equipa);
            return equipa;
        } catch (RuntimeException e) {
            throw new RuntimeException("Treinador não encontrado");
        }
    }

    public void editarNomeEquipa(UUID equipaId, String novoNome) {
        Team equipa = repo.getById(equipaId);
        if (equipa == null) {
            throw new RuntimeException("Equipa não encontrada");
        }

        equipa.setNome(novoNome);
        repo.salvar(equipa);
    }

    public Optional<Team> getById(UUID id) {
        return listarTodas().stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    public Team getByTreinadorId(UUID id) {
        return repo.getByTreinadorId(id).orElse(null);
    }

    public void adicionarJogador(UUID equipaId, UUID jogadorId) {
        Player jogador = utilizadorRepo.getPlayerById(jogadorId);
        if (jogador == null) {
            throw new RuntimeException("Jogador nao encontrado");
        }
        repo.adicionarJogador(equipaId, jogador);
    }

    public void removerJogador(UUID equipaId, UUID jogadorId) {
        repo.removerJogador(equipaId, jogadorId);
    }
}
