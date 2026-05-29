package com.senai.filmes.Model.DTO.Response;

import com.senai.filmes.Model.Enums.StatusReserva;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ReservaResponse(
        UUID id,
        SessaoResponse sessao,
        List<AssentoResponse> assentos,
        StatusReserva status,
        LocalDateTime criadoEm


) {
}
