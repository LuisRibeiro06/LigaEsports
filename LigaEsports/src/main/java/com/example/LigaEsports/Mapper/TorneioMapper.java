package com.example.LigaEsports.Mapper;

import com.example.LigaEsports.DTO.TorneioDTO;
import com.example.LigaEsports.domain.Torneio;

public class TorneioMapper {


    public static TorneioDTO toDTO(Torneio t) {
        TorneioDTO dto = new TorneioDTO(t.getId(), t.getName(), t.getTeams().size());
        return dto;
    }



}
