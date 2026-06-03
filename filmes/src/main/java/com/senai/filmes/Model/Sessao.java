package com.senai.filmes.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name= "Sessao")
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID sessao_id;

    @ManyToOne
    @JoinColumn(name = "filme_id")
    @NotNull
    private Filme filme;

    @ManyToOne
    @JoinColumn(name = "sala_id")
    @NotNull
    private Sala sala;

    @NotNull(message = "O horário do início é obrigatório!")
    private LocalDateTime inicio;


    @NotNull(message = "O horário do fim é obrigatório!")
    private LocalDateTime fim;

    @NotNull(message = "O preço é obrigatório!")
    private BigDecimal preco;
}
