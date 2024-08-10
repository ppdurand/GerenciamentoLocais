package edu.durand.GerenciamentoLocais.rest.handler;

import edu.durand.GerenciamentoLocais.rest.exception.CepIsMissingException;
import edu.durand.GerenciamentoLocais.rest.exception.LocationNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CepIsMissingException.class)
    public ResponseEntity<String> CepIsMissing(CepIsMissingException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    @ExceptionHandler(LocationNotFound.class)
    public ResponseEntity<String> locationNotFound(LocationNotFound e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}