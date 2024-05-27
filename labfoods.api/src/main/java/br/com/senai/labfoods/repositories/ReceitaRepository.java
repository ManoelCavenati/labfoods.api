package br.com.senai.labfoods.repositories;

import br.com.senai.labfoods.models.Receita;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    @Query("SELECT r, AVG(a.nota) as mediaNota FROM Receita r LEFT JOIN r.avaliacoes a GROUP BY r.id ORDER BY mediaNota DESC")
    List<Object[]> findTopRatedReceitas(Pageable pageable);
}
