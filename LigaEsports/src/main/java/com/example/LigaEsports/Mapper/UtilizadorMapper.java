package com.example.LigaEsports.Mapper;

import com.example.LigaEsports.DTO.UtilizadorDTO;
import com.example.LigaEsports.domain.*;

public class UtilizadorMapper {

    public static UtilizadorDTO toDTO(com.example.LigaEsports.domain.Utilizador utilizador) {
        UtilizadorDTO utilizadorDTO = new UtilizadorDTO();
        utilizadorDTO.setNome(utilizador.getNome());
        utilizadorDTO.setEmail(utilizador.getEmail());
        utilizadorDTO.setUsername(utilizador.getUsername());
        utilizadorDTO.setPassword(utilizador.getPassword());
        utilizadorDTO.setRole(utilizador.getRole());
        return utilizadorDTO;
    }

    public static Utilizador criarUtilizador(UtilizadorDTO dto) {
        return switch (dto.role) {
            case PLAYER -> {
                if (dto.game == null) throw new IllegalArgumentException("Jogador deve ter um jogo definido.");

                yield switch (dto.game) {
                    case FPS -> new PlayerFPS(dto.nome, dto.email, dto.username, dto.password);
                    case MOGA -> new PlayerMOGA(dto.nome, dto.email, dto.username, dto.password);
                    case EFOOTBALL -> new PlayerEFootball(dto.nome, dto.email, dto.username, dto.password);
                };
            }
            case TREINADOR -> new Treinador(dto.nome, dto.email,dto.username, dto.password);
            case ADMIN -> new Administrador(dto.nome, dto.email,dto.username, dto.password);
        };
    }
}
