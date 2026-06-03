package com.senai.filmes.DTO.Request;

public record SalaRequest(
        String nome,
        Integer totalAssentos,
        Integer fileiras,
        Integer assentosPorFileira
) {
}
