package br.com.jatao.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cliente implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String celular;

//    private String email;
//
//    private String cpf;
//
//    private String pais = "Brasil";
//
//    private boolean statusCadastro = true;
//
//    /*
//    Montar classe de endereço e mudar para EBBEDABLE
//     */
//
//    private String endereco; // MUDAR PARA EBBEDABLE
//
//    private String cidade;
//
//    private String estado;
//
//    private String cep; // montar API



}
