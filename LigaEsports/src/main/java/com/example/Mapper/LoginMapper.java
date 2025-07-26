package com.example.Mapper;

import com.example.DTO.LoginResponseDTO;
import com.example.domain.Utilizador;

public class LoginMapper {


        public static LoginResponseDTO toDTO(Utilizador u) {
            return new LoginResponseDTO(u.getId(), u.getNome(), u.getRole());
        }
    }
