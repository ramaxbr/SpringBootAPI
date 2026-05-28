package com.senai.filmes.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Assentos")
public class Assentos {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "A fileira é obrigatória.")
    private String fileira;

    @NotNull(message = "O número da fileira é obrigadtória!")
    private Integer numero;

    @ManyToOne
    @JoinColumn(name = "sala_id")
    private Sala salas;
}
