package br.com.jatao.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Schema(
        name = "Cliente",
        description = "Informações do cliente"
)
@Data
public class ClienteDto implements Serializable {

    @Schema(description = "Nome do cliente", example = "Trentor")
    @Nullable
    @Size(min = 4, max = 30, message = "O nome do cliente deve conter entre 5 e 30 caracteres")
    private String nome;

    @Schema(description = "Celular do cliente", example = "(11)999999999")
    @Pattern(regexp = "^\\d{11}$", message = "O celular deve conter 11 dígitos")
    @Nullable
    private String celular;
}
