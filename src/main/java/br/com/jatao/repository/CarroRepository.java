package br.com.jatao.repository;

import br.com.jatao.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarroRepository extends JpaRepository<Veiculo, Long> {
}
