package br.com.jatao.controller;

import br.com.jatao.dto.ClienteDto;
import br.com.jatao.exception.ObjetoNaoEncontradoException;
import br.com.jatao.exception.OrdemNaoCriadaException;
import br.com.jatao.service.ClienteService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
public class ClienteController {


    @Autowired
    ClienteService service;

    @PostMapping()
    public ResponseEntity<?> cadastrarCliente(@Valid @RequestBody ClienteDto clienteDto) {
        try {

            return ResponseEntity.status(HttpStatus.OK).body(service.cadastrarCliente(clienteDto));
        } catch (OrdemNaoCriadaException e) {
            throw new OrdemNaoCriadaException(e.getMessage());
        }
    }

    @GetMapping("/{nome}")
    public ResponseEntity<Page<ClienteDto>> consultarNomeCliente(@PageableDefault(page = 0, size = 10, sort = "id",
            direction = Sort.Direction.ASC) Pageable paginacao, @PathVariable String nome) {
        try {

            return ResponseEntity.status(HttpStatus.OK).body(service.consultarNome(nome,paginacao));
        } catch (ObjetoNaoEncontradoException e) {
            throw new ObjetoNaoEncontradoException(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<Page<ClienteDto>> listarClientes(@PageableDefault(page = 0, size = 10, sort = "id",
            direction = Sort.Direction.ASC) Pageable paginacao) {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.listarClientes(paginacao));

        } catch (ObjetoNaoEncontradoException e) {

            throw new ObjetoNaoEncontradoException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deletarOrdem(@Valid @PathVariable Long id) {
        try {
            service.deletarCliente(id);

            return ResponseEntity.ok().body("Cliente deletado com sucesso!");

        } catch (ObjetoNaoEncontradoException e) {

            throw new ObjetoNaoEncontradoException(e.getMessage());

        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ClienteDto> atualizarCliente(@Valid @PathVariable Long id,
                                                          @RequestBody ClienteDto clienteDto) {

        return service.atualizarCliente(id, clienteDto);
    }
}
