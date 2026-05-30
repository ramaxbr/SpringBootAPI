package com.senai.filmes.Model;


import com.senai.filmes.Model.Enums.GeneroFilme;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID;

//annotashows
@Entity //Importante declarar para cada classe que seja uma tabela no banco.

@Table(name = "Filmes") //Define o nome da tabela

@Data //Define a criação de get, set automaticamente
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    //UUID é para gerar automaticamente o ID do banco de dados

    @NotBlank(message = "O título é obrigatório.")
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;
    private String urlPostar;

    @NotNull(message = "o campo genero é obrigatório!")
    @Enumerated(EnumType.STRING)
    private GeneroFilme genero;

    @NotNull(message = "O campo minutos é obrigatório")
    @Min(value = 1, message = "A duração deve ser maior que 0")
    private Integer duracaoMinutos;
}
