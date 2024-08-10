package edu.durand.GerenciamentoLocais.rest.exception;

public class CepIsMissingException extends RuntimeException{
    public CepIsMissingException(){
        super("Informe o CEP");
    }
    public CepIsMissingException(String message) {
        super(message);
    }
}
