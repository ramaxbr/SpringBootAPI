package com.senai.filmes.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Reservas_Assento")

public class ReservaAssento {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @JsonIgnore //Utilizado quando a tabela é many to many
    @ManyToOne
    @JoinColumn(name = "reserva_id")
    private Reservas reservas;

    @ManyToOne
    @JoinColumn(name = "assento_id")
    private Assentos assento;



}
