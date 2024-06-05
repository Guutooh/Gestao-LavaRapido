package br.com.jatao.controller;

import br.com.jatao.dto.OrdemDeServicoDto;
import br.com.jatao.dto.ServicoDto;
import br.com.jatao.exception.ObjetoNaoEncontradoException;
import br.com.jatao.exception.OrdemNaoCriadaException;
import br.com.jatao.service.ServicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    ServicoService service;

    @PostMapping()
    public ResponseEntity<?> cadastrarServico(@Valid  @RequestBody ServicoDto servicoDto) {
        try {
            OrdemDeServicoDto ordemServico = service.criarServico(servicoDto);
            return ResponseEntity.status(HttpStatus.OK).body(ordemServico);
        } catch (OrdemNaoCriadaException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno: " + e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<List<ServicoDto>> consultarServicos() {

        try {

            List<ServicoDto> servicos = service.todosServicos();

            return ResponseEntity.status(HttpStatus.OK).body(servicos);

        } catch (ObjetoNaoEncontradoException e) {

            return ResponseEntity.notFound().build();

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
    }

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
