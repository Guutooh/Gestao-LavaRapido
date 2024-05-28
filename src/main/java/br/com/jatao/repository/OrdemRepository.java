package br.com.jatao.repository;

import br.com.jatao.model.OrdemServico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrdemRepository extends JpaRepository<OrdemServico, Long>, PagingAndSortingRepository<OrdemServico, Long> {


    @Modifying
    @Query("DELETE FROM OrdemServico o WHERE o.carro.placa = :placa")
    void deleteByCarroPlaca(String placa);


    Page<OrdemServico> findAll(  Pageable paginacao);


    Optional<OrdemServico> findByCarroPlaca(String placa);
}
