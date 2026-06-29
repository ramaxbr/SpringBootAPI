package com.Senai.Filmes.Model;

import com.Senai.Filmes.Model.Enums.GeneroFilme;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "filmes")
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @NotBlank(message = "O título é obrigatório")
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    private String urlPoster;

    @NotNull(message = "O gênero é obrigatório")
    @Enumerated(EnumType.STRING)
    private GeneroFilme genero;

    @NotNull(message = "A duração é obrigatória")
    @Min(value = 1, message = "A duração deve ser maior que 0")
    private Integer duracaoMinutos;
}
