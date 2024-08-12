package edu.durand.GerenciamentoLocais.rest.exception;

public class LocationNameIsNull extends RuntimeException{
    public LocationNameIsNull() {
        super("O nome do local não deve ser nulo");
    }

    public LocationNameIsNull(String message) {
        super(message);
    }
}
