package br.com.jatao.controller;

import br.com.jatao.Security.DadosTokenJWT;
import br.com.jatao.Security.TokenService;
import br.com.jatao.dto.UsuarioDto;
import br.com.jatao.model.UserModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid UsuarioDto dados) {

        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.getEmail(), dados.getPassword());

        var authentication = manager.authenticate(authenticationToken);

        var tokenJWT = tokenService.gerarToken((UserModel) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }


    @PostMapping("/validarToken")
    public ResponseEntity<Boolean> validarToken(@RequestBody String token) {
        boolean isValid = tokenService.isTokenValid(token);
        return ResponseEntity.ok(isValid);
    }

}
