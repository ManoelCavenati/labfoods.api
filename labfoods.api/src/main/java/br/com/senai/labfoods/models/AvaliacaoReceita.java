package br.com.senai.labfoods.models;

import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@Entity
public class AvaliacaoReceita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Nota não pode ser nula.")
    private Double nota;

    @NotBlank(message = "Feedback não pode ser vazio.")
    private String feedback;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "receita_id")
    private Receita receita;
}
