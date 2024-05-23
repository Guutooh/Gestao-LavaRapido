package br.com.jatao.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class CarroDto implements Serializable {


    private String placa;

    private String modelo;


}
