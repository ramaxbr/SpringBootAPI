package com.senai.filmes.DTO.Request;

public record LoginRequest(
        String email,
        String senha
) {
}
