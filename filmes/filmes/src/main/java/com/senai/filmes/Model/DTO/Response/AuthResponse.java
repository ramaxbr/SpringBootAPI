package com.senai.filmes.Model.DTO.Response;

public record AuthResponse(
        String token,
        String nome,
        String cargo
) {
}
