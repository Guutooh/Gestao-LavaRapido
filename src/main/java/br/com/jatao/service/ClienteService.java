package br.com.jatao.service;

import br.com.jatao.dto.ClienteDto;
import br.com.jatao.exception.ObjetoNaoEncontradoException;
import br.com.jatao.model.Cliente;
import br.com.jatao.repository.ClienteRepository;
import br.com.jatao.specifications.SpecificationTemplate;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class ClienteService {

    private ModelMapper mapper;

    private ClienteRepository clienteRepository;


    public ClienteDto cadastrarCliente(ClienteDto clienteDto) {

        Cliente cliente = mapper.map(clienteDto, Cliente.class);

        // Verificar e salvar carro se necessário
        clienteRepository.save(cliente);

        // Mapear ordemModel de volta para ordemDto
        return mapper.map(cliente, ClienteDto.class);

    }

    public Page<ClienteDto> listarClientes(SpecificationTemplate.ClienteSpec spec, Pageable pageable) {

        Page<Cliente> clientes = clienteRepository.findAll(spec, pageable);

        if (clientes == null || clientes.isEmpty()) {
            throw new ObjetoNaoEncontradoException("Nenhum cliente encontrado.");
        }

        List<ClienteDto> clienteDtoList = new ArrayList<>();
        for (Cliente cliente : clientes.getContent()) {
            clienteDtoList.add(mapper.map(cliente, ClienteDto.class));
        }

        return new PageImpl<>(clienteDtoList, pageable, clientes.getTotalElements());
    }



//    public Page<ClienteDto> listarClientes(SpecificationTemplate.ClienteSpec spec, Pageable pageable) {
//
//        Page<Cliente> clientes = clienteRepository.findAll(spec, pageable);
//
//        if (clientes.isEmpty()) {
//            throw new ObjetoNaoEncontradoException("Nenhum cliente encontrado.");
//        }
//
//        List<ClienteDto> clienteDtoList = mapClientesToDto(clientes.getContent());
//
//        return new PageImpl<>(clienteDtoList, pageable, clientes.getTotalElements());
//    }
//
//    private List<ClienteDto> mapClientesToDto(List<Cliente> clientes) {
//        return clientes.stream()
//                .map(cliente -> mapper.map(cliente, ClienteDto.class))
//                .collect(Collectors.toList());
//    }




    public void deletarCliente(Long id) {

        buscarId(id);
        clienteRepository.deleteById(id);

    }

    public Page<ClienteDto> consultarNome(String nome, Pageable paginacao) {

        Page<Cliente> clientes = clienteRepository.findByNomeContainingIgnoreCase(nome, paginacao);

        if (clientes.isEmpty()) {
            throw new ObjetoNaoEncontradoException(String.format("Nome: %s, não localizado", nome));
        }

        return clientes.map(cliente -> mapper.map(cliente, ClienteDto.class));
    }

    public Cliente buscarId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontradoException(String.format("Id: %d, não foi encontrada ", id)));
    }


    public ResponseEntity<ClienteDto> atualizarCliente(Long id, ClienteDto clienteDto) {

        Cliente clienteExistente = clienteRepository.findById(id).get();

        mapper.map(clienteDto, clienteExistente);

        clienteRepository.save(clienteExistente);

        ClienteDto dtoAtualizado = mapper.map(clienteExistente, ClienteDto.class);

        return ResponseEntity.status(HttpStatus.OK).body(dtoAtualizado);


    }
}




