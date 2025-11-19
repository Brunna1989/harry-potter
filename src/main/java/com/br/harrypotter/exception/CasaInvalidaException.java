package com.br.harrypotter.exception;

public class CasaInvalidaException extends RuntimeException{
    public CasaInvalidaException(String casa) {
        super("Casa inválida: " + casa);
    }
}
