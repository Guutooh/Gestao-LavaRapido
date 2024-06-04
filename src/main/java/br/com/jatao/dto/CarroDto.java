package br.com.jatao.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

@Schema(
        name = "carro",
        description = "informações do veiculo"
)
@Data
public class CarroDto implements Serializable {


    @Schema(description = "placa do veiculo", example = "BRA2E19")
    @NotEmpty(message = "Informe a placa do veículo")
    @Pattern(regexp = "^[\\p{L}{\\d}]{7}$", message = "Placa inválida - ex: BRA2E19 ")
    private String placa;

    @Schema( description = "modelo do veiculo", example = "corsa")
    @Size(min = 2, max = 30, message = "O nome do veiculo deve conter entre 2 e 30 caracteres")
    private String modelo;


}
