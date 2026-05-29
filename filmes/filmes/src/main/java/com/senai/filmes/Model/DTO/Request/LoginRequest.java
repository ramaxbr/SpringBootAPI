package com.senai.filmes.Model.DTO.Request;

public record LoginRequest(
        String email,
        String senha
) {
}
