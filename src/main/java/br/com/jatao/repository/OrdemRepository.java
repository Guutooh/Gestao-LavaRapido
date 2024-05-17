package br.com.jatao.repository;

import br.com.jatao.model.Ordem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdemRepository extends JpaRepository<Ordem, Long> {

    @Query("SELECT o FROM Ordem o WHERE o.carro.placa = :placa")
    Ordem ConsultarOrdem(String placa);


}
