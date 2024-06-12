package br.com.jatao.controller;

import br.com.jatao.dto.OrdemDeServicoDto;
import br.com.jatao.exception.ObjetoNaoEncontradoException;
import br.com.jatao.exception.OrdemNaoCriadaException;
import br.com.jatao.exception.error.Problem;
import br.com.jatao.service.OrdemDeServicosService;
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

@Tag(name = "Ordem de serviços", description = "CRUD REST APIs para criar, atualizar, buscar e deletar ordens de serviço")
@Validated
@RestController
@RequestMapping("/ordens")
public class OrdemDeServicoController {

    @Autowired
    OrdemDeServicosService service;

    @Operation(
            summary = "Criação das ordens de serviço",
            description = "API REST para criação de novas ordens de serviço"
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
    public ResponseEntity<?> cadastrarOrdem(@Valid @RequestBody OrdemDeServicoDto ordemServicoDto) {

            return ResponseEntity.status(HttpStatus.CREATED).body(service.criarOrdem(ordemServicoDto));

    }

    @Operation(
            summary = "Consulta de ordens de serviço por placa",
            description = "API REST para consultar ordens de serviço por placa"
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
    @GetMapping("/{placa}")
    public ResponseEntity<Page<OrdemDeServicoDto>> consultarOrdem(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable paginacao,
            @PathVariable String placa) {

            return ResponseEntity.status(HttpStatus.OK).body(service.todasOrdensPorPlaca(placa, paginacao));

    }

    @Operation(
            summary = "Listagem de ordens de serviço",
            description = "API REST para listar todas as ordens de serviço"
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
    @GetMapping()
    public ResponseEntity<Page<OrdemDeServicoDto>> listarOrdens(
            SpecificationTemplate.OrdemDeServicoSpec spec,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable paginacao) {

            return ResponseEntity.status(HttpStatus.OK).body(service.listarOrdensServico(spec,paginacao));

    }

    @Operation(
            summary = "Deleção de ordens de serviço",
            description = "API REST para deletar ordens de serviço por ID"
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
    public ResponseEntity<?> deletarOrdem(@Valid @PathVariable Long id) {

            service.deletarOrdem(id);
            return ResponseEntity.ok().body("Ordem deletada com sucesso!");

    }

    @Operation(
            summary = "Atualização de ordens de serviço",
            description = "API REST para atualizar ordens de serviço por ID"
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
    public ResponseEntity<OrdemDeServicoDto> atualizarOrdem(
            @Valid @PathVariable Long id, @RequestBody OrdemDeServicoDto ordemServicoDto) {
        return service.atualizacaoOrdemServico(id, ordemServicoDto);
    }
}
