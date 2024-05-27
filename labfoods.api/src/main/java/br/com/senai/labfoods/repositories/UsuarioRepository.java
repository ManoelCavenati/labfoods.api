package br.com.senai.labfoods.repositories;

import br.com.senai.labfoods.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCpf(String cpf);

    Optional<Usuario> findByEmail(String email);
}
