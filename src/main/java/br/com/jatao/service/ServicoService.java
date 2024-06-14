package br.com.jatao.service;

import br.com.jatao.dto.OrdemDeServicoDto;
import br.com.jatao.dto.ServicoDto;
import br.com.jatao.exception.ObjetoNaoEncontradoException;
import br.com.jatao.exception.OrdemNaoCriadaException;
import br.com.jatao.model.Cliente;
import br.com.jatao.model.Servico;
import br.com.jatao.repository.ServicoRepository;
import br.com.jatao.specifications.SpecificationTemplate;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ServicoService {


    private ServicoRepository servicoRepository;
    private ModelMapper mapper;


    public ServicoDto criarServico(@RequestBody ServicoDto servicoDto) {

        try {
            Servico servico = mapper.map(servicoDto, Servico.class);

            return mapper.map(servicoRepository.save(servico), ServicoDto.class);

        } catch (Exception e) {
            throw new OrdemNaoCriadaException("Erro ao criar serviço: " + e.getMessage());
        }
    }

    public Page<ServicoDto> listarServicos(SpecificationTemplate.ServicoSpec spec, Pageable pageable) {

        Page<Servico> servicos = servicoRepository.findAll(spec, pageable);

        if (servicos.isEmpty()) {
            throw new ObjetoNaoEncontradoException("Nenhum serviço encontrado.");
        }

        List<ServicoDto> servicoDtos = servicos.stream()
                .map(servico -> mapper.map(servico, ServicoDto.class))
                .collect(Collectors.toList());

        return new PageImpl<>(servicoDtos, pageable, servicos.getTotalElements());
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
