package br.com.software.springbootapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RegistroNaoEncontradoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RegistroNaoEncontradoException() {
        super();
    }

    public RegistroNaoEncontradoException(String erro) {
        super(erro);
    }

    public RegistroNaoEncontradoException(Throwable ex) {
        super(ex.getMessage(), ex);
    }
}
