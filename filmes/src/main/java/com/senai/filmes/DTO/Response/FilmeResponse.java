package com.senai.filmes.DTO.Response;

import com.senai.filmes.Model.Enums.GeneroFilme;

import java.util.UUID;

public record FilmeResponse (
        UUID id,
        String titulo,
        String descricao,
        String urlPostar,
        @jakarta.validation.constraints.NotNull(message = "o campo genero é obrigatório!") GeneroFilme genero,
        Integer duracaoMinutos
) {
}
