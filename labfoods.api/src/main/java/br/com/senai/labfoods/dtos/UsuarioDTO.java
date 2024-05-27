package br.com.senai.labfoods.dtos;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UsuarioDTO {
    private Long id;
    private String nome;
    private String sexo;
    private String cpf;
    private String endereco;
    private String email;
    private String senha;
    private LocalDate dataNascimento;
}
