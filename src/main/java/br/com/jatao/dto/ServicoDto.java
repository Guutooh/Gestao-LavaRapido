package br.com.jatao.dto;

import lombok.Data;

@Data
public class ServicoDto {
    private Long id;

    private String nomeServico;

    private Double valor;
}
