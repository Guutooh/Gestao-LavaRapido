package br.com.jatao.dto;

import br.com.jatao.enums.Adicionais;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrdemDeServicoDto implements Serializable {

    private Long id;

    private CarroDto carro;

    private ServicoDto servico;

    private Adicionais adicionais;

    private ClienteDto cliente;



}
