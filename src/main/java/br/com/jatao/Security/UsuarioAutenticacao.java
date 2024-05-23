package br.com.jatao.Security;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioAutenticacao {

    private String password;
    private String email;

}
