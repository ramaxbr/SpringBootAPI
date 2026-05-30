package com.senai.filmes.Model;

import com.senai.filmes.Model.Enums.StatusReserva;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
//annotashows
@Data
@NoArgsConstructor
@Entity
@Table(name = "Reservas")

public class Reservas {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "O satus não pode ser em branco")
    private StatusReserva status;

    @CreationTimestamp
    private LocalDateTime criadoEm;

    //Gerar a FOREIGN KEY entre relacionamentos.
    //Para um para muitos é necessário específicar a tabela que irá ser para muitos.
    @NotNull
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "sessao_id")
    private Sessao sessao;

    @OneToMany(mappedBy = "reservas", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservaAssento> reservaAssentos = new ArrayList<>();







}
