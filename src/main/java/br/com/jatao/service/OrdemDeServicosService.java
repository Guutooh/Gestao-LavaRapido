package br.com.jatao.service;

import br.com.jatao.dto.OrdemServicoDto;
import br.com.jatao.dto.ServicoDto;
import br.com.jatao.exception.ObjetoNaoEncontradoException;
import br.com.jatao.exception.OrdemNaoCriadaException;
import br.com.jatao.model.Carro;
import br.com.jatao.model.Cliente;
import br.com.jatao.model.OrdemServico;
import br.com.jatao.model.Servico;
import br.com.jatao.repository.CarroRepository;
import br.com.jatao.repository.ClienteRepository;
import br.com.jatao.repository.OrdemRepository;
import br.com.jatao.repository.ServicoRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

            OrdemServico ordemModel = mapper.map(ordemServicoDto, OrdemServico.class);

            ordemModel.setServico(mapper.map(ordemServicoDto.getLavagem(), Servico.class));
            ordemModel.setCarro(mapper.map(ordemServicoDto.getCarro(), Carro.class));
            ordemModel.setCliente(mapper.map(ordemServicoDto.getCliente(), Cliente.class));



//             Verificar e salvar carro se necessário
//            if (ordemModel.getCarro().getId() == null) {
               var car = carroRepository.save(ordemModel.getCarro());
                ordemModel.getCarro().setId(car.getId());
//            }

//            if (
//                    servicoService.allServicos().stream().noneMatch(servico ->
//                            ordemModel.getServico().getNomeServico().equalsIgnoreCase(servico.getNomeServico()))) {




                 var ss = servicoService.criarServico(mapper.map(ordemModel.getServico(), ServicoDto.class));
                  ordemModel.getServico().setId(ss.getId());
//            }



//            // Verificar e salvar cliente se necessário
//            if (ordemModel.getCliente().getId() == null || !clienteRepository.existsById(ordemModel.getCliente().getId())) {
               var c = clienteRepository.save(ordemModel.getCliente());
                ordemModel.getCliente().setId(c.getId());
//            }

            ordemRepository.save(ordemModel);


            // Mapear ordemModel de volta para ordemDto
            return mapper.map(ordemModel, OrdemServicoDto.class);
        } catch (OrdemNaoCriadaException e) {
            throw new OrdemNaoCriadaException("Não foi possivel criar o ordem de servico" + e.getMessage());
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