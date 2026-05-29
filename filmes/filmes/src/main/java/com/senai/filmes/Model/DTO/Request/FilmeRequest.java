package com.senai.filmes.Model.DTO.Request;

import com.senai.filmes.Model.Enums.GeneroFilme;
import jakarta.persistence.GeneratedValue;

import java.util.UUID;

public record FilmeRequest(
   UUID id,
   String titulo,
   String descricao,
   String urlPoster,
   GeneroFilme genero,
   Integer ducaracaoMinutos
) {
}
