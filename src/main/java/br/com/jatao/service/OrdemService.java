package br.com.jatao.service;

import br.com.jatao.model.Ordem;
import br.com.jatao.repository.CarroRepository;
import br.com.jatao.repository.ClienteRepository;
import br.com.jatao.repository.OrdemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrdemService {

    @Autowired
    OrdemRepository ordemRepository;

    @Autowired
    CarroRepository carroRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Transactional
    public Ordem CriarOdem(Ordem ordem) {

        // Salvar o carro se ainda não estiver salvo
        if (ordem.getCarro().getId() == null) {
            carroRepository.save(ordem.getCarro());
        }

        // Salvar o cliente se ainda não estiver salvo
        if (ordem.getCliente().getId() == null) {
            clienteRepository.save(ordem.getCliente());
        }

            return ordemRepository.save(ordem);
    }

    public ResponseEntity<?> ConsultarOdem(String placa) {



        return ResponseEntity.status(HttpStatus.OK).body(ordemRepository.ConsultarOrdem(placa));
    }
}