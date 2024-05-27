package br.com.senai.labfoods.dtos;

import java.util.List;

import lombok.Data;

@Data
public class ReceitaDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private String ingredientes;
    private String tempoPreparo;
    private String modoPreparo;
    private String tipoReceita;
    private String tipoDieta;
    private String origem;
    private Long usuarioId;
    private int quantidadeVotos;
    private List<Double> notasRecebidas;
    private double mediaNota;
}
