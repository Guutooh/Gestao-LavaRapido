package br.com.jatao.controller;

import br.com.jatao.dto.OrdemServicoDto;
import br.com.jatao.exception.ObjetoNaoEncontradoException;
import br.com.jatao.exception.OrdemNaoCriadaException;
import br.com.jatao.service.OrdemService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordem")
public class OrdemController {

    @Autowired
    OrdemService service;

    @PostMapping()
    public ResponseEntity<?> CadastrarOrdem(@RequestBody OrdemServicoDto ordemServicoDto) {
        try {

            return ResponseEntity.status(HttpStatus.OK).body(service.CriarOdem(ordemServicoDto));
        } catch (OrdemNaoCriadaException e) {
            throw new OrdemNaoCriadaException(e.getMessage());
        }
    }

    @GetMapping("/{placa}")
    public ResponseEntity<?> ConsultarOrdem(@PathVariable String placa) {
        try {

            return ResponseEntity.status(HttpStatus.OK).body(service.consultarOdem(placa));
        } catch (ObjetoNaoEncontradoException e) {
            throw new ObjetoNaoEncontradoException(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<Page<OrdemServicoDto>> ListarOrdens( @PageableDefault(page = 0, size = 10, sort = "id",
            direction = Sort.Direction.ASC) Pageable paginacao) {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.listarOrdensServico(paginacao));

        } catch (ObjetoNaoEncontradoException e) {

            throw new ObjetoNaoEncontradoException(e.getMessage());
        }
    }

    @DeleteMapping("/{placa}")
    @Transactional
    public ResponseEntity<?> deletarOrdem(@PathVariable String placa) {
        try {
            service.deletarOrdem(placa);
            return ResponseEntity.ok().body("Ordem deletado com sucesso!");
        } catch (ObjetoNaoEncontradoException e) {
            throw new ObjetoNaoEncontradoException(e.getMessage());
        }
    }

    @PutMapping("/{placa}")
    @Transactional
    public ResponseEntity<OrdemServicoDto> atualizarOrdem(@PathVariable String placa,
                                                          @RequestBody OrdemServicoDto ordemServicoDto) {

        return service.atualizacaoOrdemServico(placa, ordemServicoDto);
    }

}
