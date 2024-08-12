package edu.durand.GerenciamentoLocais.rest.exception;

public class LocationNotFoundException extends RuntimeException{
    public LocationNotFoundException() {
        super("Local Não Encontrado. Tente um ID existente");
    }

    public LocationNotFoundException(String message) {
        super(message);
    }
}
