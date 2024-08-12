package edu.durand.GerenciamentoLocais.rest.exception;

public class LocationNameIsNullException extends RuntimeException{
    public LocationNameIsNullException() {
        super("O nome do local não deve ser nulo");
    }

    public LocationNameIsNullException(String message) {
        super(message);
    }
}
