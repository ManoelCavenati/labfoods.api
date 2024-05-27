package br.com.senai.labfoods.dtos;

import lombok.Data;

@Data
public class AvaliacaoReceitaDTO {
    private Long id;
    private Long usuarioId;
    private Long receitaId;
    private Double nota;
    private String feedback;
}
