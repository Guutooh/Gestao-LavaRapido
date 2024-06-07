package br.com.jatao.repository;

import br.com.jatao.model.OrdemDeServico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdemDeServicoRepository extends JpaRepository<OrdemDeServico, Long>, PagingAndSortingRepository<OrdemDeServico, Long> {



    Page<OrdemDeServico> findAll(Pageable paginacao);



    Page<OrdemDeServico> findByCarroPlacaContainingIgnoreCase(String placa, Pageable pageable);
}
