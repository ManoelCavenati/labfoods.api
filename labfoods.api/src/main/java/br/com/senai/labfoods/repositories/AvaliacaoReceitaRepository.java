package br.com.senai.labfoods.repositories;

import br.com.senai.labfoods.models.AvaliacaoReceita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoReceitaRepository extends JpaRepository<AvaliacaoReceita, Long> {

}
