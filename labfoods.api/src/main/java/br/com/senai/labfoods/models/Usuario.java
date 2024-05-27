package br.com.senai.labfoods.models;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome não pode ser vazio.")
    private String nome;

    @NotBlank(message = "Sexo não pode ser vazio.")
    private String sexo;

    @NotBlank(message = "CPF não pode ser vazio.")
    @Column(unique = true)
    private String cpf;

    @NotBlank(message = "Endereço não pode ser vazio.")
    private String endereco;

    @Email(message = "Email deve ser válido.")
    @NotBlank(message = "Email não pode ser vazio.")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Senha não pode ser vazia.")
    private String senha;

    @NotNull(message = "Data de nascimento não pode ser nula.")
    private LocalDate dataNascimento;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Receita> receitas;

    private String role;
}
