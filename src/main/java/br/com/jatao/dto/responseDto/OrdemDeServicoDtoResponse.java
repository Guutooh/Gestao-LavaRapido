package br.com.jatao.dto.responseDto;

import br.com.jatao.dto.ClienteDto;
import br.com.jatao.dto.ServicoDto;
import br.com.jatao.dto.VeiculoDto;
import br.com.jatao.enums.Adicionais;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Schema(
        name = "Ordem De Servico Response",
        description = "Ordem de serviço para resposta"
)
@Data
public class OrdemDeServicoDtoResponse implements Serializable {

    @Schema(description = "Identificador único da ordem de serviço", example = "1")
    private Long id;

    @Schema(description = "Informações do veículo")
    private VeiculoDto carro;

    @Schema(description = "Informações do serviço")
    private ServicoDto servico;

    @Schema(description = "Informações de adicionais")
    private Adicionais adicionais;

    @Schema(description = "Informações do cliente")
    private ClienteDto cliente;

    @Schema(description = "Data da Ordem de Serviço", example = "17-06-14 15:30")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataDaOrdem; // Novo campo adicionado
}