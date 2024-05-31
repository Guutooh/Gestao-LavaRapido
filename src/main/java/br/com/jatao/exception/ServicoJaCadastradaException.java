package br.com.jatao.exception;

import java.io.Serial;

public class ServicoJaCadastradaException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ServicoJaCadastradaException(String message) {
        super(message);
    }

    public ServicoJaCadastradaException(String message, Throwable cause) {
        super(message, cause);
    }
}
