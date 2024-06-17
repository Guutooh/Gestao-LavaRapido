package br.com.jatao.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class EnderecoDto {

    @NotNull(message="é necessário informar CEP")
    private String cep;

    @NotBlank(message="é necessário informar numero da residencia")
    @Length(min = 1)
    private String numero;

    @Nullable
    private String complemento;
}
