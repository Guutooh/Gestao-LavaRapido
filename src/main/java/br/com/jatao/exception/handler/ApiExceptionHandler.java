package br.com.jatao.exception.handler;

import br.com.jatao.exception.OrdemNaoCriadaException;
import br.com.jatao.exception.ServicoJaCadastradaException;
import br.com.jatao.exception.error.Problem;
import br.com.jatao.exception.error.ProblemType;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

//
//    //capturar todas exceções não tratadas
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
//
//        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
//        ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
//        String detail = "Ocorreu um erro interno inesperado no sistema. "
//                + "Tente novamente e se o problema persistir, entre em contato "
//                + "com o administrador do sistema.";
//
//
//        LocalDateTime hora = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
//        String formattedHora = hora.format(formatter);
//
//        Problem problem = Problem.builder()
//                .status(status.value())
//                .type(problemType.getUri())
//                .title(problemType.getTitle())
//                .detail(detail)
//                .hora(hora)
//                .build();
//
//        return ResponseEntity.status(status).body(problem);
//    }


    @ExceptionHandler(ServicoJaCadastradaException.class)
    public ResponseEntity<?> handleServicoJaCadastradaException(ServicoJaCadastradaException ex, WebRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        ProblemType problemType = ProblemType.SERVICO_JA_CADASTRADO;
        String detail = ex.getMessage();
        LocalDateTime hora = LocalDateTime.now();

        Problem problem = Problem.builder()
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail)
                .hora(hora)
                .build();

        return new ResponseEntity<>(problem, new HttpHeaders(), status);
    }


    @ExceptionHandler(OrdemNaoCriadaException.class)
    public ResponseEntity<?> handleOrdemNaoCriadaExceptionException(OrdemNaoCriadaException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.ERRO_AO_CRIAR_ORDEM;
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(status, problemType, detail)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationExceptionException(DataIntegrityViolationException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.ERRO_AO_CRIAR_ORDEM;
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(status, problemType, detail)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemType problemType = ProblemType.DADOS_INVALIDOS;
        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

        BindingResult bindingResult = ex.getBindingResult();

        List<Problem.Field> fields = bindingResult.getFieldErrors()
                .stream().map(fieldError -> Problem.Field.builder()
                        .name(fieldError.getField())
                        .userMessage(fieldError.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        Problem problem = createProblemBuilder((HttpStatus) status, problemType, detail)
                .userMessage(detail)
                .filds(fields)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }


    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;

        String detail = MessageFormat.format("O recurso {0}, que você tentou acessar, é inexistente.", ex.getRequestURL());


        Problem problem = createProblemBuilder((HttpStatus) status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }


    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;

        String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
                        + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

        Problem problem = createProblemBuilder(status, problemType, detail)
                .userMessage(detail)//não necessario
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch(
                    (MethodArgumentTypeMismatchException) ex, headers, (HttpStatus) status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    private String joinPath(List<JsonMappingException.Reference> references) {
        return references.stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {

        return Problem.builder()
                .hora(LocalDateTime.now())
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail);
    }

}
