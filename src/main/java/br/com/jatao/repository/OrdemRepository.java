package br.com.jatao.repository;

import br.com.jatao.model.OrdemServico;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrdemRepository extends JpaRepository<OrdemServico, Long> {

    @Transactional
    @Query("SELECT o FROM OrdemServico o WHERE o.carro.placa = :placa")
    Optional<OrdemServico> findByCarroPlaca(String placa);

    @Modifying
    @Query("DELETE FROM OrdemServico o WHERE o.carro.placa = :placa")
    void deleteByCarroPlaca(String placa);




}
