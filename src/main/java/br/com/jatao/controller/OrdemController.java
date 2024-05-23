package br.com.jatao.controller;

import br.com.jatao.dto.OrdemDto;
import br.com.jatao.exception.ObjetoNaoEncontradoException;
import br.com.jatao.exception.OrdemNaoCriadaException;
import br.com.jatao.service.OrdemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ordem")
public class OrdemController {

    @Autowired
    OrdemService service;

    @PostMapping()
    public ResponseEntity<?> CadastrarOrdem(@RequestBody OrdemDto ordemDto) {
        try{

        return ResponseEntity.status(HttpStatus.OK).body(service.CriarOdem(ordemDto));
        }catch(OrdemNaoCriadaException e){
            throw new OrdemNaoCriadaException(e.getMessage());
        }
    }

    @GetMapping("{placa}")
    public ResponseEntity<?> ConsultarOrdem(@PathVariable String placa) {
        try{

        return ResponseEntity.status(HttpStatus.OK).body(service.ConsultarOdem(placa));
        }catch(ObjetoNaoEncontradoException e){
            throw new ObjetoNaoEncontradoException(e.getMessage());
        }
    }


}
