package edu.durand.GerenciamentoLocais.rest.exception;

public class LocationNameInvalid extends RuntimeException{
    public LocationNameInvalid() {
        super("O nome do local precisa de no mínimo 3 caracteres");
    }


    public LocationNameInvalid(String message) {
        super(message);
    }
}
