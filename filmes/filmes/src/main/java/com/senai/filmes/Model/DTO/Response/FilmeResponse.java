package com.senai.filmes.Model.DTO.Response;

import com.senai.filmes.Model.Enums.GeneroFilme;

import java.util.UUID;

public record FilmeResponse (
      UUID id,
      String titulo,
      String descricao,
      String urlPostar,
      String genero,
      Integer duracaoMinutos
) {
}
