package br.com.jatao.service;

import br.com.jatao.dto.OrdemServicoDto;
import br.com.jatao.dto.ServicoDto;
import br.com.jatao.exception.ObjetoNaoEncontradoException;
import br.com.jatao.exception.OrdemNaoCriadaException;
import br.com.jatao.model.Servico;
import br.com.jatao.repository.ServicoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicoService {

    @Autowired
    ServicoRepository servicoRepository;

    @Autowired
    private ModelMapper mapper;

    public OrdemServicoDto criarServico(@RequestBody ServicoDto servicoDto) {
        try {
            Servico servico = mapper.map(servicoDto, Servico.class);
            return mapper.map(servicoRepository.save(servico), OrdemServicoDto.class);
        } catch (Exception e) {
            throw new OrdemNaoCriadaException("Erro ao criar serviço: " + e.getMessage());
        }
    }

    public List<ServicoDto> allServicos() {
        try {
            List<Servico> servicos = servicoRepository.findAll();
            if (servicos.isEmpty()) {
            }
            return servicos.stream()
                    .map(servico -> mapper.map(servico, ServicoDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ObjetoNaoEncontradoException("Erro ao buscar serviços: " + e.getMessage());
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
