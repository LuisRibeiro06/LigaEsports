package com.example.LigaEsports.Mapper;

import com.example.LigaEsports.DTO.LoginResponseDTO;
import com.example.LigaEsports.domain.Utilizador;

public class LoginMapper {


        public static LoginResponseDTO toDTO(Utilizador u) {
            return new LoginResponseDTO(u.getId(), u.getNome(), u.getRole());
        }
    }
