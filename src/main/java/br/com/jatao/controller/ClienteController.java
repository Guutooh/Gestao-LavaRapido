package br.com.jatao.controller;

import br.com.jatao.dto.ClienteDto;
import br.com.jatao.exception.error.Problem;
import br.com.jatao.service.ClienteService;
import br.com.jatao.specifications.SpecificationTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Clientes", description = "APIs REST para criação, atualização, busca e deleção de clientes")
@Validated
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    ClienteService service;

    @Operation(
            summary = "Cadastro de novo cliente",
            description = "API REST para cadastrar um novo cliente"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Status HTTP CRIADO"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Status HTTP Erro Interno do Servidor",
                    content = @Content(
                            schema = @Schema(implementation = Problem.class)
                    )
            )
    })
    @PostMapping()
    public ResponseEntity<?> cadastrarCliente(@Valid @RequestBody ClienteDto clienteDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrarCliente(clienteDto));
    }

    @Operation(
            summary = "Consulta de clientes por nome",
            description = "API REST para consultar clientes por nome"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Status HTTP OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Status HTTP Não Encontrado",
                    content = @Content(
                            schema = @Schema(implementation = Problem.class)
                    )
            )
    })

    @GetMapping("/{id}")
    public ResponseEntity<?> consultarClientePorID(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarId(id));
    }


    @GetMapping("consultarNomeCliente/{nome}")
    public ResponseEntity<Page<ClienteDto>> consultarNomeCliente(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable paginacao,
            @PathVariable String nome) {
        return ResponseEntity.status(HttpStatus.OK).body(service.consultarNome(nome, paginacao));
    }

    @Operation(
            summary = "Listagem de clientes",
            description = "API REST para listar todos os clientes"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Status HTTP OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Status HTTP Não Encontrado",
                    content = @Content(
                            schema = @Schema(implementation = Problem.class)
                    )
            )
    })

    @GetMapping("/listar")
    public ResponseEntity<Page<ClienteDto>> listarTodosClientes(SpecificationTemplate.ClienteSpec spec,
                                                                @PageableDefault(page = 0, size = 10, sort = "id",
                                                                   direction = Sort.Direction.ASC) Pageable paginacao) {
        return ResponseEntity.status(HttpStatus.OK).body(service.listarClientes(spec, paginacao));
    }


    @Operation(
            summary = "Deleção de cliente",
            description = "API REST para deletar cliente por ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Status HTTP OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Status HTTP Não Encontrado",
                    content = @Content(
                            schema = @Schema(implementation = Problem.class)
                    )
            )
    })
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deletarCliente(@Valid @PathVariable Long id) {
        service.deletarCliente(id);
        return ResponseEntity.ok().body("Cliente deletado com sucesso!");
    }


    @Operation(
            summary = "Atualização de cliente",
            description = "API REST para atualizar cliente por ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Status HTTP OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Status HTTP Não Encontrado",
                    content = @Content(
                            schema = @Schema(implementation = Problem.class)
                    )
            )
    })
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ClienteDto> atualizarCliente(
            @Valid @PathVariable Long id, @RequestBody ClienteDto clienteDto) {
        return service.atualizarCliente(id, clienteDto);
    }
}
