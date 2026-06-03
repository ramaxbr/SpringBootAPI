package com.senai.filmes.DTO.Response;

import java.math.BigDecimal;
import java.util.List;

public record RelatorioResponse(
        long totalReservas,
        BigDecimal totalReceita,
        List<FilmesTotais> filmes
) {
    public record FilmesTotais(String nomeFilme, long totalReservas){
        
    }
}
