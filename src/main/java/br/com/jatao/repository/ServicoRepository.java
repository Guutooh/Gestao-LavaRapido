package br.com.jatao.repository;

import br.com.jatao.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServicoRepository extends JpaRepository<Servico,Long> {


    Optional<Servico> findByNomeServicoIgnoreCase(String nomeServico);
}
