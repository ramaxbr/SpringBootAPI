package com.Senai.Filmes.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "sessoes")
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @NotNull(message = "O filme é obrigatório")
    @ManyToOne
    @JoinColumn(name = "filme_id")
    private Filme filme;

    @NotNull(message = "A sala é obrigatória")
    @ManyToOne
    @JoinColumn(name = "sala_id")
    private Sala sala;

    @NotNull(message = "O horário de início é obrigatório")
    private LocalDateTime inicio;

    @NotNull(message = "O horário de fim é obrigatório")
    private LocalDateTime fim;

    @NotNull(message = "O preço é obrigatório")
    private BigDecimal preco;
}
