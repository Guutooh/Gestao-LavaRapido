package br.com.jatao.repository;

import br.com.jatao.model.Cliente;
import br.com.jatao.model.OrdemServico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {


    Page<Cliente> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

}
