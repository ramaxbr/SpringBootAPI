package com.Senai.Filmes.DTO.Request;

import com.Senai.Filmes.Model.Enums.GeneroFilme;

public record FilmeRequest(
        String titulo,
        String descricao,
        GeneroFilme genero,
        Integer duracaoMinutos
) {}
