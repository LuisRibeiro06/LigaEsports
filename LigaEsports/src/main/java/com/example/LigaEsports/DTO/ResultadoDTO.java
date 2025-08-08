package com.example.LigaEsports.DTO;

import java.util.List;
import java.util.UUID;

public class ResultadoDTO {
    private UUID partidaId;
    private String resultado;

    private List<EstatisticaJogadorDTO> estatisticasJogadores;

    public ResultadoDTO() {
    }

    public ResultadoDTO(UUID partidaId, String resultado, List<EstatisticaJogadorDTO> estatisticasJogadores) {
        this.partidaId = partidaId;
        this.resultado = resultado;
        this.estatisticasJogadores = estatisticasJogadores;
    }

    public UUID getPartidaId() {
        return partidaId;
    }

    public void setPartidaId(UUID partidaId) {
        this.partidaId = partidaId;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public List<EstatisticaJogadorDTO> getEstatisticasJogadores() {
        return estatisticasJogadores;
    }

    public void setEstatisticasJogadores(List<EstatisticaJogadorDTO> estatisticasJogadores) {
        this.estatisticasJogadores = estatisticasJogadores;
    }
}
