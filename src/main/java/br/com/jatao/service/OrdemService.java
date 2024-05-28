package br.com.jatao.service;

import br.com.jatao.dto.OrdemServicoDto;
import br.com.jatao.exception.ObjetoNaoEncontradoException;
import br.com.jatao.model.OrdemServico;
import br.com.jatao.repository.CarroRepository;
import br.com.jatao.repository.ClienteRepository;
import br.com.jatao.repository.OrdemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
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


    public Page<OrdemServicoDto> listarOrdensServico(@PageableDefault(page = 0, size = 10, sort = "id",
            direction = Sort.Direction.ASC) Pageable paginacao) {

        try {
            Page<OrdemServico> ordens = ordemRepository.findAll(paginacao);

            return ordens.map(ordemServico -> mapper.map(ordemServico, OrdemServicoDto.class));

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

        ordemRepository.save(ordemExistente);

        OrdemServicoDto dtoAtualizado = mapper.map(ordemExistente, OrdemServicoDto.class);

        return ResponseEntity.status(HttpStatus.OK).body(dtoAtualizado);


    }

    public OrdemServico localizarPlaca(String placa) {
        return ordemRepository.findByCarroPlaca(placa)
                .orElseThrow(() -> new ObjetoNaoEncontradoException(String.format("Placa: %s, não foi encontrada ", placa)));
    }



}