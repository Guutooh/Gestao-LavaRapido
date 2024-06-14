package br.com.jatao.repository;

import br.com.jatao.model.Cliente;
import br.com.jatao.model.Servico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServicoRepository extends JpaRepository<Servico,Long>, JpaSpecificationExecutor<Servico>  {


    Optional<Servico> findByNomeServicoIgnoreCase(String nomeServico);

    Page<Servico> findAll(Specification<Servico> spec, Pageable pageable);

}
