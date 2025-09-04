package com.example.LigaEsports.Mapper;

import com.example.LigaEsports.DTO.PlayerCreateDTO;
import com.example.LigaEsports.DTO.PlayerResponseDTO;
import com.example.LigaEsports.DTO.PlayerUpdateDTO;
import com.example.LigaEsports.domain.*;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper {

    public Player toEntity(PlayerCreateDTO dto) {
        Player player;

        // Factory pattern baseado no tipo de jogo
        switch (dto.getTipoJogo()) {
            case FPS:
                PlayerFPS fps = new PlayerFPS();
                fps.setPrecisao(dto.getPrecisao() != null ? dto.getPrecisao() : 0.0);
                fps.setHeadshots(dto.getHeadshots() != null ? dto.getHeadshots() : 0);
                player = fps;
                break;

            case MOBA:
                PlayerMOBA moba = new PlayerMOBA();
                moba.setPersonagem(dto.getPersonagem());
                moba.setNum_kills(dto.getKills() != null ? dto.getKills() : 0);
                moba.setNum_deaths(dto.getDeaths() != null ? dto.getDeaths() : 0);
                moba.setNum_assists(dto.getAssists() != null ? dto.getAssists() : 0);
                player = moba;
                break;

            case EFOOTBALL:
                PlayerEFootball ef = new PlayerEFootball();
                ef.setPosition(dto.getPosition());
                ef.setNum_goals(dto.getGoals() != null ? dto.getGoals() : 0);
                ef.setNum_assists(dto.getAssistencias() != null ? dto.getAssistencias() : 0);
                player = ef;
                break;

            default:
                throw new IllegalArgumentException("Tipo de jogo não suportado: " + dto.getTipoJogo());
        }

        // Propriedades comuns
        player.setNome(dto.getName());
        player.setEmail(dto.getEmail());
        player.setUsername(dto.getUsername());
        player.setNum_games(0); // Iniciando com 0 jogos

        return player;
    }

    public static PlayerResponseDTO toResponseDTO(Player player) {
        PlayerResponseDTO dto = new PlayerResponseDTO();

        // Propriedades comuns
        dto.setId(player.getId());
        dto.setName(player.getNome());
        dto.setEmail(player.getEmail());
        dto.setUsername(player.getUsername());
        dto.setNumGames(player.getNum_games());

        // Propriedades específicas baseadas no tipo
        if (player instanceof PlayerFPS fps) {
            dto.setTipoJogo(Game.FPS);
            dto.setPrecisao(fps.getPrecisao());
            dto.setHeadshots(fps.getHeadshots());
        } else if (player instanceof PlayerMOBA moba) {
            dto.setTipoJogo(Game.MOBA);
            dto.setPersonagem(moba.getPersonagem());
            dto.setKills(moba.getNum_kills());
            dto.setDeaths(moba.getNum_deaths());
            dto.setAssists(moba.getNum_assists());
        } else if (player instanceof PlayerEFootball ef) {
            dto.setTipoJogo(Game.EFOOTBALL);
            dto.setPosition(ef.getPosition());
            dto.setGoals(ef.getNum_goals());
            dto.setAssistencias(ef.getNum_assists());
        }

        return dto;
    }

    public static Player updateFromDTO(Player existingPlayer, PlayerUpdateDTO dto) {
        // Atualizar propriedades comuns apenas se não forem nulas
        if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
            existingPlayer.setNome(dto.getName());
        }
        if (dto.getEmail() != null && !dto.getEmail().trim().isEmpty()) {
            existingPlayer.setEmail(dto.getEmail());
        }
        if (dto.getUsername() != null && !dto.getUsername().trim().isEmpty()) {
            existingPlayer.setUsername(dto.getUsername());
        }

        // Atualizar propriedades específicas baseadas no tipo
        if (existingPlayer instanceof PlayerFPS fps && dto.getPrecisao() != null) {
            fps.setPrecisao(dto.getPrecisao());
        } else if (existingPlayer instanceof PlayerMOBA moba && dto.getPersonagem() != null) {
            moba.setPersonagem(dto.getPersonagem());
        } else if (existingPlayer instanceof PlayerEFootball ef && dto.getPosition() != null) {
            ef.setPosition(dto.getPosition());
        }

        return existingPlayer;
    }
}