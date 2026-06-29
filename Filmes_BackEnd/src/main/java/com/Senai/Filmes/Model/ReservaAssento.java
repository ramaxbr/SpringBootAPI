package com.Senai.Filmes.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "reservas_assentos")
public class ReservaAssento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "reserva_id")
    @JsonIgnore
    private Reserva reserva;

    @ManyToOne
    @JoinColumn(name = "assento_id")
    private Assento assento;
}
