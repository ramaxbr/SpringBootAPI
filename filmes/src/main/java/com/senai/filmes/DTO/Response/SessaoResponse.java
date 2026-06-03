package com.senai.filmes.DTO.Response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record SessaoResponse(
        UUID id,
        FilmeResponse filme,
        LocalDateTime inicio,
        LocalDateTime fim,
        BigDecimal preco
) {
}
