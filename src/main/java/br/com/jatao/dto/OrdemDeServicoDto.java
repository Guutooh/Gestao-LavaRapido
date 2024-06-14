package br.com.jatao.dto;

import br.com.jatao.enums.Adicionais;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

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
    private CarroDto carro;

    @Schema(description = "Informações do serviço")
    @Valid
    private ServicoDto servico;

    @Schema(description = "Informações de adicionais")
    @Valid
    private Adicionais adicionais;

    @Schema(description = "Informações do cliente")
    @Valid
    private ClienteDto cliente;

}
