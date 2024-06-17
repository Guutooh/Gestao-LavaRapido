package br.com.jatao.service;

import br.com.jatao.client.ViacepClient;
import br.com.jatao.dto.ClienteDto;
import br.com.jatao.dto.EnderecoDto;
import br.com.jatao.exception.ObjetoNaoEncontradoException;
import br.com.jatao.model.Cliente;
import br.com.jatao.model.Endereco;
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

    private ViacepClient viacepClient;

    public ClienteDto cadastrarCliente(ClienteDto clienteDto) {


        // Chamada ao serviço ViaCEP
        var viaCep = viacepClient.getEndereco(clienteDto.getEnderecoDto().getCep());

        Cliente cliente = mapper.map(clienteDto, Cliente.class);

        Endereco endereco = cliente.getEndereco();
        endereco.setRua(viaCep.getLogradouro());
        endereco.setBairro(viaCep.getBairro());
        endereco.setCidade(viaCep.getLocalidade());
        endereco.setUf(viaCep.getUf());

        Cliente clienteSalvo = clienteRepository.save(cliente);

        EnderecoDto enderecoDtoSalvo = mapper.map(clienteSalvo.getEndereco(), EnderecoDto.class);

        clienteDto.setEnderecoDto(enderecoDtoSalvo);

        return mapper.map(clienteDto, ClienteDto.class);
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




