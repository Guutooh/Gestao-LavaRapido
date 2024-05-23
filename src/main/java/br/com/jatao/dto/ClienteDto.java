package br.com.jatao.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class ClienteDto implements Serializable {



    private String nome;

    private String celular;
}
