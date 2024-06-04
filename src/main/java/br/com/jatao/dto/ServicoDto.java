package br.com.jatao.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import java.io.Serializable;

@Schema(
        name = "Servico",
        description = "informações do serviço"
)
@Data
public class ServicoDto implements Serializable {


    @Schema(description = "Identificador único do serviço")
    private Long id;

    @Schema(description = "Nome do serviço prestado", example = "completa")
    @NotBlank(message = "O nome do serviço é obrigatório")
    @Size(min = 5, max = 30, message = "O nome do serviço deve conter entre 2 e 30 caracteres")
    private String nomeServico;

    @Schema(description = "Valor do serviço")
    @DecimalMax(value = "99999.99", message = "O valor máximo permitido é R$ 99.999,99")
    @PositiveOrZero(message = "O valor do serviço deve ser maior ou igual a zero")
    @Digits(integer = 5, fraction = 2, message = "O valor deve ter no máximo 5 dígitos inteiros e 2 casas decimais")
    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##0.00")
    private Double valor;


    private void converterValor() {
        if (valor != null) {
            String valorStr = valor.toString();

            if (valorStr.contains(",")) {
                valorStr = valorStr.replace(",", ".");
                valor = Double.parseDouble(valorStr);
            }
        }
    }
}


