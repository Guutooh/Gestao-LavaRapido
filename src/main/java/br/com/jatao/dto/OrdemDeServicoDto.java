package br.com.jatao.dto;

import br.com.jatao.enums.Adicionais;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.Data;

import java.io.Serializable;

@Schema(
        name = "Ordem De Servico",
        description = "Ordem de serviço"
)
@Data
public class OrdemDeServicoDto implements Serializable {

    @Schema(description = "Identificador único da ordem de serviço", example = "1")
    private Long id;

    @Schema(description = "Informações do veículo")
    @Valid
    private VeiculoDto carro;

    @Schema(description = "Informações do serviço")
    @Valid
    private ServicoDto servico;

    @Schema(description = "Informações de adicionais")
    @Valid
    @Nullable
    private Adicionais adicionais;

    @Schema(description = "Informações do cliente")
    @Valid
    @Nullable
    private ClienteDto cliente;

}
