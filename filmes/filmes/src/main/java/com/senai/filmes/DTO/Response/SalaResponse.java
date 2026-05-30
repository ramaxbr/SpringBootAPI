package com.senai.filmes.DTO.Response;

import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.UUID;

public record SalaResponse(
        UUID id,
        String nome,
        Integer totalAssentos
) {
}
