package com.senai.filmes.Model;

import com.senai.filmes.Model.Enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "reservas")

public class reservas {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @CreationTimestamp
    private LocalDateTime criadoEm;

    @NotBlank(message = "O satus não pode ser em branco")
    private Status status;

    @ManyToOne(mappedBy )




}
