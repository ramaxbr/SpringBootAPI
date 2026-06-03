package com.senai.filmes.Model.DTO.Request;

public record SalaRequest(
        String nome,
        Integer totalAssentos,
        Integer fileiras,
        Integer assentosPorFileira
) {
}
