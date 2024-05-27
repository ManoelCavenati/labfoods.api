package br.com.senai.labfoods.dtos;

import lombok.Data;

@Data
public class UsuarioPublicDTO {
    private Long id;
    private String nome;
    private String sexo;
    private String email;
}
