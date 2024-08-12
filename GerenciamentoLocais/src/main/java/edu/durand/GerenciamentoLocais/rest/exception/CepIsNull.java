package edu.durand.GerenciamentoLocais.rest.exception;

public class CepIsNull extends RuntimeException{
    public CepIsNull() {
        super("O CEP não pode ser nulo");
    }

    public CepIsNull(String message) {
        super(message);
    }
}
