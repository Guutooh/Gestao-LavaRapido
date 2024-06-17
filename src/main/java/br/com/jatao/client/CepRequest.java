package br.com.jatao.client;

import lombok.Data;

@Data
public class CepRequest {

   private String cep;
   private String logradouro;
   private String bairro;
   private String localidade;
   private String uf;

}
