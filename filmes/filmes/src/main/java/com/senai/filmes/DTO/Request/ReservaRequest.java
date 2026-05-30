package com.senai.filmes.DTO.Request;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ReservaRequest(
        UUID sessaoId,
        BigDecimal totalReceita,
        List<FilmeTotais> filmes
) {
    public record FilmeTotais(String nomeFilme, long totalReservas){

    }
}
