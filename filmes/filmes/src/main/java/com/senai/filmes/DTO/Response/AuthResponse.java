package com.senai.filmes.DTO.Response;

public record AuthResponse(
        String token,
        String nome,
        String cargo
) {
}
