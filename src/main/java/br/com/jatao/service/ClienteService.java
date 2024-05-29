package br.com.jatao.service;

import br.com.jatao.dto.ClienteDto;
import br.com.jatao.dto.OrdemServicoDto;
import br.com.jatao.exception.ObjetoNaoEncontradoException;
import br.com.jatao.model.Cliente;
import br.com.jatao.model.OrdemServico;
import br.com.jatao.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ClienteService {


    private ModelMapper mapper;

    private ClienteRepository clienteRepository;


    public ClienteDto cadastrarCliente(ClienteDto clienteDto) {

        var cliente = mapper.map(clienteDto, Cliente.class);

        // Verificar e salvar carro se necessário

        clienteRepository.save(cliente);

        // Mapear ordemModel de volta para ordemDto
        return mapper.map(cliente, ClienteDto.class);

    }

    public Page<ClienteDto> listarClientes(Pageable paginacao) {

        try {
            Page<Cliente> clientes = clienteRepository.findAll(paginacao);

            return clientes.map(c -> mapper.map(clientes, ClienteDto.class));

        } catch (ObjetoNaoEncontradoException e) {
            throw new ObjetoNaoEncontradoException(e.getMessage());
        }


    }

    public void deletarCliente(Long id) {

        localizarId(id);
        clienteRepository.deleteById(id);

    }

    public Page<ClienteDto> consultarNome(String nome, Pageable paginacao) {

        try {
            Page<Cliente> cliente = clienteRepository.findByNomeContainingIgnoreCase(nome, paginacao);

            return cliente.map(c -> mapper.map(cliente, ClienteDto.class));

        } catch (ObjetoNaoEncontradoException e) {
            throw new ObjetoNaoEncontradoException(e.getMessage());
        }

    }




    public Cliente localizarId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontradoException(String.format("Id: %d, não foi encontrada ", id)));
    }


    public Page<ClienteDto> buscaClientes(String nome, Pageable pageable) {
        Page<Cliente> search = clienteRepository.findByNomeContainingIgnoreCase(nome, pageable);

        if (search.isEmpty()) {
            throw new ObjetoNaoEncontradoException(String.format("nome: %s, não foi encontrado ", nome));
        }
        return search.map(c -> mapper.map(search, ClienteDto.class));
    }


    public ResponseEntity<ClienteDto> atualizarCliente(Long id, ClienteDto clienteDto) {

        Cliente clienteExistente = clienteRepository.findById(id).get();

        mapper.map(clienteDto, clienteExistente);

        clienteRepository.save(clienteExistente);

        ClienteDto dtoAtualizado = mapper.map(clienteExistente, ClienteDto.class);

        return ResponseEntity.status(HttpStatus.OK).body(dtoAtualizado);


    }
}




