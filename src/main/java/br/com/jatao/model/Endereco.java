package br.com.jatao.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Endereco {

    private String pais = "Brasil";
    private String cep;
    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
}
