package br.com.jatao.dto;

import br.com.jatao.enums.Adicionais;
import br.com.jatao.enums.Lavagem;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrdemDto implements Serializable {

    private Long id;

    private CarroDto carro;

    private Lavagem lavagem;

    private Adicionais adicionais;

    private ClienteDto cliente;



}
