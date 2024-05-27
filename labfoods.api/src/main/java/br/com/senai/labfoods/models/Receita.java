package br.com.senai.labfoods.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Data
@Entity
public class Receita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Título não pode ser vazio.")
    private String titulo;

    @NotBlank(message = "Descrição não pode ser vazia.")
    private String descricao;

    @NotBlank(message = "Ingredientes não podem ser vazios.")
    private String ingredientes;

    @NotBlank(message = "Tempo de preparo não pode ser vazio.")
    private String tempoPreparo;

    @NotBlank(message = "Modo de preparo não pode ser vazio.")
    private String modoPreparo;

    @NotBlank(message = "Tipo de receita não pode ser vazio.")
    private String tipoReceita;

    @NotBlank(message = "Tipo de dieta não pode ser vazio.")
    private String tipoDieta;

    @NotBlank(message = "Origem não pode ser vazia.")
    private String origem;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "receita", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AvaliacaoReceita> avaliacoes = new ArrayList<>();
}
