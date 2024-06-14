package br.com.jatao.dto;

import br.com.jatao.model.Cliente;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Schema(
        name = "Historico",
        description = "Histórico de ordens de serviço"
)
@Data
public class HistoricoDto implements Serializable {

    @Schema(description = "Identificador único do histórico", example = "1")
    private Long id;

    @Schema(description = "Informações do carro relacionado ao histórico")
    private VeiculoDto veiculoDto;

    @Schema(description = "Informações do cliente relacionado ao histórico")
    private Cliente cliente;

    @Schema(description = "Lista de ordens de serviço relacionadas ao histórico")
    private List<OrdemDeServicoDto> ordemServicoDtos;
}
