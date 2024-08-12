package edu.durand.GerenciamentoLocais.rest.exception;

public class LocationNameIsInvalidException extends RuntimeException{
    public LocationNameIsInvalidException() {
        super("O nome do local precisa de no mínimo 3 caracteres");
    }


    public LocationNameIsInvalidException(String message) {
        super(message);
    }
}
