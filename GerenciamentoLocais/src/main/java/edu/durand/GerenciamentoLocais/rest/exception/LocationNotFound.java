package edu.durand.GerenciamentoLocais.rest.exception;

public class LocationNotFound extends RuntimeException{
    public LocationNotFound() {
        super("Local Não Encontrado. Tente um ID existente");
    }

    public LocationNotFound(String message) {
        super(message);
    }
}
