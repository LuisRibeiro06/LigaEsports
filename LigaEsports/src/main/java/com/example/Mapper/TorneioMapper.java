package com.example.Mapper;

import com.example.DTO.TorneioDTO;
import com.example.domain.Torneio;

public class TorneioMapper {


    public static TorneioDTO toDTO(Torneio t) {
        TorneioDTO dto = new TorneioDTO(t.getId(), t.getName(), t.getTeams().size());
        return dto;
    }



}
