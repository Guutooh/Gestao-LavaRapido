package br.com.jatao.dto;

import br.com.jatao.enums.Adicionais;
import br.com.jatao.enums.Lavagem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
public class OrdemServicoDto implements Serializable {

    private Long id;

    private CarroDto carro;

    private Lavagem lavagem;

    private Adicionais adicionais;

    private ClienteDto cliente;



}
