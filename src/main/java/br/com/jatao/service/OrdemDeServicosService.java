package br.com.jatao.service;

import br.com.jatao.dto.OrdemDeServicoDto;
import br.com.jatao.exception.ObjetoNaoEncontradoException;
import br.com.jatao.exception.OrdemNaoCriadaException;
import br.com.jatao.exception.ServicoJaCadastradaException;
import br.com.jatao.model.OrdemDeServico;
import br.com.jatao.model.Servico;
import br.com.jatao.repository.CarroRepository;
import br.com.jatao.repository.ClienteRepository;
import br.com.jatao.repository.OrdemRepository;
import br.com.jatao.repository.ServicoRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class OrdemDeServicosService {


    OrdemRepository ordemRepository;

    CarroRepository carroRepository;

    ClienteRepository clienteRepository;

    ServicoRepository servicoRepository;

    private ModelMapper mapper;

    private ClienteService clienteService;

    private ServicoService servicoService;

    public OrdemDeServicoDto CriarOdem(OrdemDeServicoDto ordemServicoDto) {

        try {

            // Mapear o DTO para a entidade OrdemServico
            OrdemDeServico ordemModel = mapper.map(ordemServicoDto, OrdemDeServico.class);

            // Verificar e salvar carro se necessário
            if (ordemModel.getCarro().getId() == null) {
                carroRepository.save(ordemModel.getCarro());
            }

            // Verificar e salvar cliente se necessário
            if (ordemModel.getCliente().getId() == null || clienteService.buscarId(ordemModel.getCliente().getId()) == null) {
                clienteRepository.save(ordemModel.getCliente());
            }

            // Verificar e salvar serviço se necessário
            if (ordemModel.getServico() != null) {
                var nomeServico = ordemModel.getServico().getNomeServico();
                Optional<Servico> servicoExistenteOptional = servicoRepository.findFirstByNomeServicoIgnoreCase(nomeServico);

                servicoExistenteOptional.ifPresentOrElse(
                        servico -> {
                            ordemModel.getServico().setNomeServico(servico.getNomeServico());
                            ordemModel.getServico().setValor(servico.getValor());
                            servicoRepository.save(ordemModel.getServico());
                        },
                        () -> ordemModel.setServico(servicoRepository.save(ordemModel.getServico()))
                );
            }

//            //verifica os serviços existeste e salva se necessário
//            if (ordemModel.getServico() != null ) {
//
//                var nomeServico = ordemModel.getServico().getNomeServico();
//
//                Optional<Servico> servicoExistenteOptional =
//                       servicoRepository.findFirstByNomeServicoIgnoreCase(nomeServico);
//
//                if (servicoExistenteOptional.isPresent()) {
//
//                    Servico servico = servicoExistenteOptional.get();
//
//                    ordemModel.getServico().setNomeServico(servico.getNomeServico());
//                    ordemModel.getServico().setValor(servico.getValor());
//
//                    servicoRepository.save(ordemModel.getServico());

//                } else {
//                    ordemModel.setServico(servicoRepository.save(ordemModel.getServico()));
//                }
//            }

            ordemRepository.save(ordemModel);

            return mapper.map(ordemModel, OrdemDeServicoDto.class);

        } catch (OrdemNaoCriadaException e) {
            throw new OrdemNaoCriadaException("Ordem não criada.");
        }

    }

    public Page<OrdemDeServicoDto> listarOrdensServico(@PageableDefault(page = 0, size = 10, sort = "id",
            direction = Sort.Direction.ASC) Pageable paginacao) {

        try {
            Page<OrdemDeServico> ordens = ordemRepository.findAll(paginacao);

            return ordens.map(ordemServico -> mapper.map(ordemServico, OrdemDeServicoDto.class));

        } catch (ObjetoNaoEncontradoException e) {
            throw new ObjetoNaoEncontradoException(e.getMessage());
        }
    }

    public void deletarOrdem(Long id) {

        OrdemDeServico ordem = localizarId(id);

        ordemRepository.deleteByCarroPlaca(id);
    }

    public ResponseEntity<OrdemDeServicoDto> atualizacaoOrdemServico(Long id, OrdemDeServicoDto ordemServicoDto) {

        OrdemDeServico ordemExistente = ordemRepository.findById(id).get();

        mapper.map(ordemServicoDto, ordemExistente);

        ordemRepository.save(ordemExistente);

        OrdemDeServicoDto dtoAtualizado = mapper.map(ordemExistente, OrdemDeServicoDto.class);

        return ResponseEntity.status(HttpStatus.OK).body(dtoAtualizado);
    }


    public OrdemDeServico localizarId(Long id) {
        return ordemRepository.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontradoException(String.format("Id: %d, não foi encontrada ",
                        id)));
    }

    public Page<OrdemDeServicoDto> todasOrdensPorPlaca(String placa, Pageable pageable) {
        Page<OrdemDeServico> search = ordemRepository.findByCarroPlacaContainingIgnoreCase(placa, pageable);

        if (search.isEmpty()) {
            throw new ObjetoNaoEncontradoException(String.format("Placa: %s, não foi encontrada ", placa));
        }
        return search.map(ordemServico -> mapper.map(ordemServico, OrdemDeServicoDto.class));
    }
}