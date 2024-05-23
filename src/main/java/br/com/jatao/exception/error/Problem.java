package br.com.jatao.exception.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class Problem {

    private Integer status;
    private String type;
    private String title;
    private String detail;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime hora;
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
