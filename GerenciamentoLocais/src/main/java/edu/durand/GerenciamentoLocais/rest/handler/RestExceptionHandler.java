package edu.durand.GerenciamentoLocais.rest.handler;

import edu.durand.GerenciamentoLocais.rest.exception.*;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Order(1)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CepIsMissingException.class)
    public ResponseEntity<String> CepIsMissing(CepIsMissingException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    @ExceptionHandler(LocationNotFoundException.class)
    public ResponseEntity<String> locationNotFound(LocationNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    @ExceptionHandler(LocationNameIsInvalidException.class)
    public ResponseEntity<String> locationNameInvalid(LocationNameIsInvalidException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    @ExceptionHandler(CepIsNullException.class)
    public ResponseEntity<String> cepIsNull(CepIsNullException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    @ExceptionHandler(LocationNameIsNullException.class)
    public ResponseEntity<String> locationNameisNull(LocationNameIsNullException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
