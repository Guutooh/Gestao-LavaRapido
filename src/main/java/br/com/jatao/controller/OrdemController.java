package br.com.jatao.controller;

import br.com.jatao.model.Ordem;
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
    public ResponseEntity<?> CadastrarOrdem(@RequestBody Ordem ordem) {
        return ResponseEntity.status(HttpStatus.OK).body(service.CriarOdem(ordem));
    }

    @GetMapping("{placa}")
    public ResponseEntity<?> ConsultarOrdem(@PathVariable String placa) {
        return ResponseEntity.status(HttpStatus.OK).body(service.ConsultarOdem(placa));
    }

}
