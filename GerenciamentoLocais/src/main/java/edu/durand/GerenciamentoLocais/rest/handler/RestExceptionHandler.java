package edu.durand.GerenciamentoLocais.rest.handler;

import edu.durand.GerenciamentoLocais.rest.exception.*;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Order(1)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CepIsMissingException.class)
    public ResponseEntity<String> CepIsMissing(CepIsMissingException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    @ExceptionHandler(LocationNotFound.class)
    public ResponseEntity<String> locationNotFound(LocationNotFound e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    @ExceptionHandler(LocationNameInvalid.class)
    public ResponseEntity<String> locationNameInvalid(LocationNameInvalid e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    @ExceptionHandler(CepIsNull.class)
    public ResponseEntity<String> cepIsNull(CepIsNull e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    @ExceptionHandler(LocationNameIsNull.class)
    public ResponseEntity<String> locationNameisNull(LocationNameIsNull e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
