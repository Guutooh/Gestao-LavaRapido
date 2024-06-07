package br.com.jatao.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Schema(
        name = "Carro",
        description = "Informações do veículo"
)
@Data
public class CarroDto implements Serializable {

    @Schema(description = "Placa do veículo", example = "BRA2E19")
    @NotEmpty(message = "Informe a placa do veículo")
    @Pattern(regexp = "^[\\p{L}{\\d}]{7}$", message = "Placa inválida - ex: BRA2E19")
    private String placa;

    @Schema(description = "Modelo do veículo", example = "Corsa")
    @Size(min = 2, max = 30, message = "O nome do veículo deve conter entre 2 e 30 caracteres")
    private String modelo;

}
