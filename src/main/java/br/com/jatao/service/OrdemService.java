package br.com.jatao.service;

import br.com.jatao.dto.OrdemDto;
import br.com.jatao.model.OrdemServico;
import br.com.jatao.repository.CarroRepository;
import br.com.jatao.repository.ClienteRepository;
import br.com.jatao.repository.OrdemRepository;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper mapper;

    public OrdemDto CriarOdem(OrdemDto ordemDto) {

        var ordemModel = mapper.map(ordemDto, OrdemServico.class);

        // Verificar e salvar carro se necessário
        if (ordemModel.getCarro().getId() == null) {

            carroRepository.save(ordemModel.getCarro());
        }

        // Verificar e salvar cliente se necessário
        if (ordemModel.getCliente().getId() == null) {
            clienteRepository.save(ordemModel.getCliente());
        }

        ordemRepository.save(ordemModel);

        // Mapear ordemModel de volta para ordemDto
        return mapper.map(ordemModel, OrdemDto.class);

    }

    public ResponseEntity<?> ConsultarOdem(String placa) {

        return ResponseEntity.status(HttpStatus.OK).body(getOrdemServico(placa));
    }

    private OrdemServico getOrdemServico(String placa) {

        OrdemServico ordem = ordemRepository.ConsultarOrdem(placa);

        if (ordem == null) {
            throw new RuntimeException(String.format("Placa: %s, não foi encontrada ", placa));
        }

        return ordem;

    }

}