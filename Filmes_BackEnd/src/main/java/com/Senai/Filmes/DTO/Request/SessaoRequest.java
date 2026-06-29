package com.Senai.Filmes.DTO.Request;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record SessaoRequest(
        UUID filmeId,
        UUID salaId,
        LocalDateTime inicio,
        LocalDateTime fim,
        BigDecimal preco
) {}
