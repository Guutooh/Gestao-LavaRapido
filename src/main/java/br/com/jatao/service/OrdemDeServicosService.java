package br.com.jatao.service;

import br.com.jatao.dto.ClienteDto;
import br.com.jatao.dto.OrdemDeServicoDto;
import br.com.jatao.dto.ServicoDto;
import br.com.jatao.dto.VeiculoDto;
import br.com.jatao.dto.responseDto.OrdemDeServicoDtoResponse;
import br.com.jatao.exception.ObjetoNaoEncontradoException;
import br.com.jatao.exception.OrdemNaoCriadaException;
import br.com.jatao.model.OrdemDeServico;
import br.com.jatao.model.Servico;
import br.com.jatao.repository.ClienteRepository;
import br.com.jatao.repository.OrdemDeServicoRepository;
import br.com.jatao.repository.ServicoRepository;
import br.com.jatao.repository.VeiculoRepository;
import br.com.jatao.specifications.SpecificationTemplate;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class OrdemDeServicosService {


    OrdemDeServicoRepository ordemDeServicoRepository;

    VeiculoRepository veiculoRepository;

    ClienteRepository clienteRepository;

    ServicoRepository servicoRepository;

    private ModelMapper mapper;

    private ClienteService clienteService;

    /**
     * Cria uma nova ordem de serviço.
     *
     * @param ordemServicoDto os detalhes da ordem de serviço a ser criada
     * @return a ordem de serviço criada
     * @throws OrdemNaoCriadaException se houver um erro durante a criação da ordem
     */

    public OrdemDeServicoDto criarOrdem(OrdemDeServicoDto ordemServicoDto) {
        try {
            OrdemDeServico ordemModel = mapper.map(ordemServicoDto, OrdemDeServico.class);

            if (ordemModel.getCarro().getId() == null) {
                ordemModel.setCarro(veiculoRepository.save(ordemModel.getCarro()));
            }

            if (ordemModel.getCliente() != null && ordemModel.getCliente().getId() == null) {
                ordemModel.setCliente(clienteRepository.save(ordemModel.getCliente()));
            }


            var nomeServico = ordemModel.getServico().getNomeServico();
            Optional<Servico> servicoExistenteOptional = servicoRepository.findByNomeServicoIgnoreCase(nomeServico);

            if (servicoExistenteOptional.isPresent()) {
                ordemModel.setServico(servicoExistenteOptional.get());
            } else {
                ordemModel.setServico(servicoRepository.save(ordemModel.getServico()));
            }

            ordemDeServicoRepository.save(ordemModel);

            return mapper.map(ordemModel, OrdemDeServicoDto.class);
        } catch (Exception e) {
            throw new OrdemNaoCriadaException("Ordem não criada.", e);
        }
    }


    public Page<OrdemDeServicoDtoResponse> listarOrdensServico(SpecificationTemplate.OrdemDeServicoSpec spec, @PageableDefault(sort = "id",
            direction = Sort.Direction.ASC) Pageable paginacao) {

        try {
            Page<OrdemDeServico> ordens = ordemDeServicoRepository.findAll(spec, paginacao);

            return ordens.map(ordemServico -> mapper.map(ordemServico, OrdemDeServicoDtoResponse.class));

        } catch (ObjetoNaoEncontradoException e) {
            throw new ObjetoNaoEncontradoException(e.getMessage());
        }
    }

    public void deletarOrdem(Long id) {

        OrdemDeServico idServico = getIdServico(id);
        ordemDeServicoRepository.deleteById(idServico.getId());

    }

    public ResponseEntity<OrdemDeServicoDto> atualizacaoOrdemServico(Long id, OrdemDeServicoDto ordemServicoDto) {

        OrdemDeServico ordemExistente = getIdServico(id);

        mapper.map(ordemServicoDto, ordemExistente);

        ordemDeServicoRepository.save(ordemExistente);

        OrdemDeServicoDto dtoAtualizado = mapper.map(ordemExistente, OrdemDeServicoDto.class);
        dtoAtualizado.getCliente().setNome(ordemExistente.getCliente().getNome());

        return ResponseEntity.status(HttpStatus.OK).body(dtoAtualizado);
    }


    public Page<OrdemDeServicoDtoResponse> todasOrdensPorPlaca(SpecificationTemplate.OrdemDeServicoSpec spec,
                                                       String placa,
                                                       Pageable pageable) {

        Specification<OrdemDeServico> placaSpec = SpecificationTemplate.ConsultarOrdensPorPlaca(placa);
        Specification<OrdemDeServico> combinedSpec = Specification.where(spec).and(placaSpec);

        Page<OrdemDeServico> search = ordemDeServicoRepository.findAll(combinedSpec, pageable);

        if (search.isEmpty()) {
            throw new ObjetoNaoEncontradoException(String.format("Placa: %s, não localizada", placa));
        }

        return search.map(ordemServico -> {
            OrdemDeServicoDtoResponse dto = new OrdemDeServicoDtoResponse();
            dto.setId(ordemServico.getId());
            dto.setCarro(mapper.map(ordemServico.getCarro(), VeiculoDto.class));
            dto.setServico(mapper.map(ordemServico.getServico(), ServicoDto.class));
            dto.setAdicionais(ordemServico.getAdicionais());
            if (ordemServico.getCliente() != null) {
                dto.setCliente(mapper.map(ordemServico.getCliente(), ClienteDto.class));
            }
            dto.setDataDaOrdem(ordemServico.getDataDaOrdem()); // Adicionando a data da ordem

            return dto;
        });
    }


    private OrdemDeServico getIdServico(Long id) {
        return ordemDeServicoRepository.findById(id).orElseThrow(
                () -> new ObjetoNaoEncontradoException((String.format("ID: %d, não localizado ", id))));
    }


}