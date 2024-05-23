package br.com.jatao.service;

import br.com.jatao.dto.OrdemServicoDto;
import br.com.jatao.exception.ObjetoNaoEncontradoException;
import br.com.jatao.model.Carro;
import br.com.jatao.model.Cliente;
import br.com.jatao.model.OrdemServico;
import br.com.jatao.repository.CarroRepository;
import br.com.jatao.repository.ClienteRepository;
import br.com.jatao.repository.OrdemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    public OrdemServicoDto CriarOdem(OrdemServicoDto ordemServicoDto) {

        var ordemModel = mapper.map(ordemServicoDto, OrdemServico.class);

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
        return mapper.map(ordemModel, OrdemServicoDto.class);

    }

    public ResponseEntity<?> consultarOdem(String placa) {

        return ResponseEntity.status(HttpStatus.OK).body(localizarPlaca(placa));
    }


    public List<OrdemServicoDto> listarOrdensServico() {

        try {
            List<OrdemServico> ordens = ordemRepository.findAll();


            return ordens.stream()
                    .map(ordemServico -> mapper.map(ordemServico, OrdemServicoDto.class))
                    .collect(Collectors.toList());
        } catch (ObjetoNaoEncontradoException e) {
            throw new ObjetoNaoEncontradoException(e.getMessage());
        }

    }
    public void deletarOrdem(String placa) {

        OrdemServico ordem = localizarPlaca(placa);

        ordemRepository.deleteByCarroPlaca(placa);
    }



    public ResponseEntity<OrdemServicoDto> atualizacaoOrdemServico(String placa, OrdemServicoDto ordemServicoDto) {

        OrdemServico ordemExistente = localizarPlaca(placa);

        mapper.map(ordemServicoDto, ordemExistente);

//        Carro carro = ordemExistente.getCarro();
//        Cliente cliente = ordemExistente.getCliente();

//        ordemExistente.setCarro(carro);
//        ordemExistente.setCliente(cliente);


        ordemRepository.save(ordemExistente);

        OrdemServicoDto dtoAtualizado = mapper.map(ordemExistente, OrdemServicoDto.class);

        return ResponseEntity.status(HttpStatus.OK).body(dtoAtualizado);


    }



    public OrdemServico localizarPlaca(String placa) {
        return ordemRepository.findByCarroPlaca(placa)
                .orElseThrow(() -> new ObjetoNaoEncontradoException(String.format("Placa: %s, não foi encontrada ", placa)));
    }

}