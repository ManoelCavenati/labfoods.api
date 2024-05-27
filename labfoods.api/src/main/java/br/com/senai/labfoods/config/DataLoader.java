package br.com.senai.labfoods.config;

import br.com.senai.labfoods.models.Usuario;
import br.com.senai.labfoods.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Configuration
public class DataLoader {

        @Autowired
        private UsuarioRepository usuarioRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Bean
        public CommandLineRunner loadDatabase() {
                return args -> {
                        createUsuarioIfNotFound("12345678901", "João", "Masculino", "Endereço 1", "joao@example.com",
                                        "senha123", LocalDate.of(1990, 1, 1));
                        createUsuarioIfNotFound("23456789012", "Maria", "Feminino", "Endereço 2", "maria@example.com",
                                        "senha123", LocalDate.of(1992, 2, 2));
                        createUsuarioIfNotFound("34567890123", "Carlos", "Masculino", "Endereço 3",
                                        "carlos@example.com", "senha123", LocalDate.of(1994, 3, 3));
                        createUsuarioIfNotFound("45678901234", "Ana", "Feminino", "Endereço 4", "ana@example.com",
                                        "senha123", LocalDate.of(1996, 4, 4));
                        createUsuarioIfNotFound("56789012345", "Pedro", "Masculino", "Endereço 5", "pedro@example.com",
                                        "senha123", LocalDate.of(1998, 5, 5));
                };
        }

        private void createUsuarioIfNotFound(String cpf, String nome, String sexo, String endereco, String email,
                        String senha, LocalDate dataNascimento) {
                if (!usuarioRepository.findByCpf(cpf).isPresent()) {
                        Usuario usuario = new Usuario();
                        usuario.setNome(nome);
                        usuario.setSexo(sexo);
                        usuario.setCpf(cpf);
                        usuario.setEndereco(endereco);
                        usuario.setEmail(email);
                        usuario.setSenha(passwordEncoder.encode(senha));
                        usuario.setDataNascimento(dataNascimento);
                        usuario.setRole("ROLE_USER");
                        usuarioRepository.save(usuario);
                }
        }
}
