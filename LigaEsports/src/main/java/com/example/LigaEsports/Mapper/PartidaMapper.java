package com.example.LigaEsports.Mapper;

import com.example.LigaEsports.DTO.PartidaDTO;
import com.example.LigaEsports.domain.Partida;

public class PartidaMapper {

    public static PartidaDTO toDto(Partida partida){
        return new PartidaDTO(partida.getTeam1().getId(), partida.getTeam2().getId(), partida.getData());
    }
}
