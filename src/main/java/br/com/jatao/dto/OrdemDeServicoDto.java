package br.com.jatao.dto;

import br.com.jatao.enums.Adicionais;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Data;

import java.io.Serializable;

@Schema(description = "Ordem de serviços")
@Data
public class OrdemDeServicoDto implements Serializable {

    private Long id;

    @Schema(description = "informações do veiculo")
    @Valid
    private CarroDto carro;


    @Schema(description = "informações do serviço")
    @Valid
    private ServicoDto servico;

    @Schema(description = "informações de adicionais")
    @Valid
    private Adicionais adicionais;

    @Valid
    @Schema(description = "informações do cliente")
    private ClienteDto cliente;


}
