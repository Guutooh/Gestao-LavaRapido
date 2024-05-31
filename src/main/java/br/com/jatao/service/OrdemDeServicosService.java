package br.com.jatao.service;

import br.com.jatao.dto.OrdemServicoDto;
import br.com.jatao.exception.ObjetoNaoEncontradoException;
import br.com.jatao.exception.OrdemNaoCriadaException;
import br.com.jatao.exception.ServicoJaCadastradaException;
import br.com.jatao.model.OrdemServico;
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

    public OrdemServicoDto CriarOdem(OrdemServicoDto ordemServicoDto) {


        try {

            // Mapear o DTO para a entidade OrdemServico
            OrdemServico ordemModel = mapper.map(ordemServicoDto, OrdemServico.class);

            // Verificar e salvar carro se necessário
            if (ordemModel.getCarro().getId() == null) {
                carroRepository.save(ordemModel.getCarro());
            }

            // Verificar e salvar cliente se necessário
            if (ordemModel.getCliente().getId() == null || clienteService.buscarId(ordemModel.getCliente().getId()) == null) {
                clienteRepository.save(ordemModel.getCliente());
            }

            //verifica os serviços existeste e salva se necessário
            if (ordemModel.getServico() != null && ordemModel.getServico().getId() == null && ordemModel.getServico().getNomeServico() != null) {

                var nomeServico = ordemModel.getServico().getNomeServico();

                Optional<Servico> servicoExistenteOptional = servicoRepository.findByNomeServicoIgnoreCase(nomeServico);

                if (servicoExistenteOptional.isPresent()) {
                    throw new ServicoJaCadastradaException("Já existe um serviço com mesmo nome cadastrado no " +
                            "sistema.");
                }else {

                Servico servicoExistente = servicoService.todosServicos().stream()
                        .filter(servicoDto -> nomeServico.equalsIgnoreCase(servicoDto.getNomeServico()))
                        .map(servicoDto -> mapper.map(servicoDto, Servico.class))
                        .findFirst()
                        .orElseGet(() -> servicoRepository.save(mapper.map(ordemModel.getServico(), Servico.class)));

                // Associar o serviço à ordem de serviço antes de salvar
                ordemModel.setServico(servicoExistente);
                }
            }
            // Salvar a ordem de serviço criado
            ordemRepository.save(ordemModel);

            // Mapear a ordem de serviço salva de volta para o DTO
            return mapper.map(ordemModel, OrdemServicoDto.class);

        } catch (OrdemNaoCriadaException e) {
            throw new OrdemNaoCriadaException("Ordem não criada.");
        }

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

    public void deletarOrdem(Long id) {

        OrdemServico ordem = localizarId(id);

        ordemRepository.deleteByCarroPlaca(id);
    }

    public ResponseEntity<OrdemServicoDto> atualizacaoOrdemServico(Long id, OrdemServicoDto ordemServicoDto) {

        OrdemServico ordemExistente = ordemRepository.findById(id).get();

        mapper.map(ordemServicoDto, ordemExistente);

        ordemRepository.save(ordemExistente);

        OrdemServicoDto dtoAtualizado = mapper.map(ordemExistente, OrdemServicoDto.class);

        return ResponseEntity.status(HttpStatus.OK).body(dtoAtualizado);
    }


    public OrdemServico localizarId(Long id) {
        return ordemRepository.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontradoException(String.format("Id: %d, não foi encontrada ",
                        id)));
    }

    public Page<OrdemServicoDto> todasOrdensPorPlaca(String placa, Pageable pageable) {
        Page<OrdemServico> search = ordemRepository.findByCarroPlacaContainingIgnoreCase(placa, pageable);

        if (search.isEmpty()) {
            throw new ObjetoNaoEncontradoException(String.format("Placa: %s, não foi encontrada ", placa));
        }
        return search.map(ordemServico -> mapper.map(ordemServico, OrdemServicoDto.class));
    }
}