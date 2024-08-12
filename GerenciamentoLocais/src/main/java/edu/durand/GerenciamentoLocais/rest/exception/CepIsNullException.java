package edu.durand.GerenciamentoLocais.rest.exception;

public class CepIsNullException extends RuntimeException{
    public CepIsNullException() {
        super("O CEP não pode ser nulo");
    }

    public CepIsNullException(String message) {
        super(message);
    }
}
