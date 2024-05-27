package br.com.senai.labfoods.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AvaliacaoPropriaReceitaException extends RuntimeException {
    public AvaliacaoPropriaReceitaException(String message) {
        super(message);
    }
}
