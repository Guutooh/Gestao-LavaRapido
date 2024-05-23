package br.com.jatao.repository;

import br.com.jatao.model.OrdemServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrdemRepository extends JpaRepository<OrdemServico, Long> {

    @Query("SELECT o FROM OrdemServico o WHERE o.carro.placa = :placa")
    Optional<OrdemServico> findByCarroPlaca(String placa);




}
