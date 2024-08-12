package edu.durand.GerenciamentoLocais.rest.validation;

import edu.durand.GerenciamentoLocais.application.dto.LocationDTO;
import edu.durand.GerenciamentoLocais.rest.exception.CepIsMissingException;
import edu.durand.GerenciamentoLocais.rest.exception.CepIsNull;
import edu.durand.GerenciamentoLocais.rest.exception.LocationNameInvalid;
import edu.durand.GerenciamentoLocais.rest.exception.LocationNameIsNull;
import org.springframework.stereotype.Component;


@Component
public class LocationValidator {
    public void validateLocation(LocationDTO location){
        if(location.cep() == null){
            throw new CepIsNull();
        }
        if(location.cep().isBlank()){
            throw new CepIsMissingException();
        }
        if(location.name() == null){
            throw new LocationNameIsNull();
        }
        if(location.name().isBlank() || location.name().length() <= 2){
            throw new LocationNameInvalid();
        }
    }

}
