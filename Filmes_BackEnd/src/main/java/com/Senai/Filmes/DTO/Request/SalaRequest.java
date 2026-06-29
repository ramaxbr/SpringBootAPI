package com.Senai.Filmes.DTO.Request;

public record SalaRequest(
        String nome,
        Integer totalAssentos,
        Integer fileiras,
        Integer assentosPorFileira
) {}
