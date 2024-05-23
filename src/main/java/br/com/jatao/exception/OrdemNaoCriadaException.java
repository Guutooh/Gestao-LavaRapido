package br.com.jatao.exception;

import java.io.Serial;

public class OrdemNaoCriadaException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public OrdemNaoCriadaException(String message) {
        super(message);
    }

    public OrdemNaoCriadaException(String message, Throwable cause) {
        super(message, cause);
    }
}
