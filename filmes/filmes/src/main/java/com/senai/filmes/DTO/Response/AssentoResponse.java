package com.senai.filmes.DTO.Response;
import java.util.UUID;

public record AssentoResponse(
        UUID id,
        String fileira,
        Integer numero,
        boolean disponivel
) {
}
