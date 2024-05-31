package br.com.jatao.repository;

import br.com.jatao.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServicoRepository extends JpaRepository<Servico,Long> {

    Optional<Servico> findFirstByNomeServicoIgnoreCase(String nomeServico);

    @Query("SELECT s.id FROM Servico s WHERE LOWER(s.nomeServico) = LOWER(:nomeServico)")
    Optional<Long> findIdByNomeServicoIgnoreCase(@Param("nomeServico") String nomeServico);

}
