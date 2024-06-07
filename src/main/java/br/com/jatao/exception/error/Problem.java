package br.com.jatao.exception.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;


@Schema(
        name = "ErrorResponse",
        description = "Esquema para conter informações de resposta de erro"
)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class Problem {


    @Schema(
            description = "Status HTTP da resposta"
    )
    private Integer status;

    @Schema(
            description = "Tipo do problema"
    )
    private String type;

    @Schema(
            description = "Título do problema"
    )
    private String title;

    @Schema(
            description = "Detalhe do problema"
    )
    private String detail;

    @Schema(
            description = "Hora em que o problema ocorreu",
            example = "dd/MM/yyyy HH:mm"
    )
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime hora;

    @Schema(
            description = "Mensagem amigável para o usuário"
    )
    private String userMessage;

    //retorno constratints violadas.

    private List<Field> filds;

    @Getter
    @Builder
    public static class Field{
        private String name;
        private String userMessage;
    }



}
