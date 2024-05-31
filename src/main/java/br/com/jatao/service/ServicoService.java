package br.com.jatao.service;

import br.com.jatao.dto.OrdemDeServicoDto;
import br.com.jatao.dto.ServicoDto;
import br.com.jatao.exception.ObjetoNaoEncontradoException;
import br.com.jatao.exception.OrdemNaoCriadaException;
import br.com.jatao.model.Servico;
import br.com.jatao.repository.ServicoRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ServicoService {


    private ServicoRepository servicoRepository;
    private ModelMapper mapper;


    public OrdemDeServicoDto criarServico(@RequestBody ServicoDto servicoDto) {
        try {
            Servico servico = mapper.map(servicoDto, Servico.class);
            return mapper.map(servicoRepository.save(servico), OrdemDeServicoDto.class);
        } catch (Exception e) {
            throw new OrdemNaoCriadaException("Erro ao criar serviço: " + e.getMessage());
        }
    }

    public List<ServicoDto> todosServicos() {

        try {

            List<Servico> servicos = servicoRepository.findAll();

            return servicos.stream()
                    .map(servico -> mapper.map(servico, ServicoDto.class))
                    .collect(Collectors.toList());

        } catch (ObjetoNaoEncontradoException e) {

            throw new ObjetoNaoEncontradoException("Não foi encontrados serviços: " + e.getMessage());
        }
    }

    public void excluirServico(Long id) {
        try {
            Servico servico = servicoRepository.findById(id)
                    .orElseThrow(() -> new ObjetoNaoEncontradoException("Serviço não encontrado com o ID: " + id));
            servicoRepository.delete(servico);
        } catch (Exception e) {
            throw new ObjetoNaoEncontradoException("Erro ao excluir serviço: " + e.getMessage());
        }
    }
}
