package br.com.jatao.controller;

import br.com.jatao.dto.OrdemDeServicoDto;
import br.com.jatao.dto.ServicoDto;
import br.com.jatao.exception.ObjetoNaoEncontradoException;
import br.com.jatao.exception.OrdemNaoCriadaException;
import br.com.jatao.exception.error.Problem;
import br.com.jatao.service.ServicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Serviços", description = "APIs REST para criação, consulta e exclusão de serviços")
@RestController
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    ServicoService service;

    @Operation(
            summary = "Cadastro de novo serviço",
            description = "API REST para cadastrar um novo serviço"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Status HTTP CRIADO"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Status HTTP Requisição Inválida",
                    content = @Content(
                            schema = @Schema(implementation = Problem.class)
                    )
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
    public ResponseEntity<?> cadastrarServico(@Valid @RequestBody ServicoDto servicoDto) {
        try {
            OrdemDeServicoDto ordemServico = service.criarServico(servicoDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(ordemServico);
        } catch (OrdemNaoCriadaException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno: " + e.getMessage());
        }
    }

    @Operation(
            summary = "Consulta de todos os serviços",
            description = "API REST para consultar todos os serviços"
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
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Status HTTP Erro Interno do Servidor",
                    content = @Content(
                            schema = @Schema(implementation = Problem.class)
                    )
            )
    })
    @GetMapping()
    public ResponseEntity<List<ServicoDto>> consultarServicos() {
        try {
            List<ServicoDto> servicos = service.todosServicos();
            return ResponseEntity.status(HttpStatus.OK).body(servicos);
        } catch (ObjetoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(
            summary = "Exclusão de serviço",
            description = "API REST para excluir um serviço por ID"
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
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Status HTTP Erro Interno do Servidor",
                    content = @Content(
                            schema = @Schema(implementation = Problem.class)
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirServico(@Valid @PathVariable Long id) {
        try {
            service.excluirServico(id);
            return ResponseEntity.status(HttpStatus.OK).body("Serviço excluído com sucesso");
        } catch (ObjetoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno: " + e.getMessage());
        }
    }
}
