package br.com.jatao.dto;


import br.com.jatao.model.Cliente;
import lombok.Data;

import java.util.List;

@Data
public class HistoricoDto {


    private Long id;

    private CarroDto carroDto;

    private Cliente cliente;

    List<OrdemDto> ordemDtos;

}
