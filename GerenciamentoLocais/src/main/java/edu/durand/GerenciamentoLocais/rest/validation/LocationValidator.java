package edu.durand.GerenciamentoLocais.rest.validation;

import edu.durand.GerenciamentoLocais.application.dto.LocationDTO;
import edu.durand.GerenciamentoLocais.domain.model.Location;
import edu.durand.GerenciamentoLocais.rest.exception.*;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class LocationValidator {
    public void validateLocation(LocationDTO location){
        if(location.cep() == null){
            throw new CepIsNullException();
        }
        if(location.cep().isBlank()){
            throw new CepIsMissingException();
        }
        if(location.name() == null){
            throw new LocationNameIsNullException();
        }
        if(location.name().isBlank() || location.name().length() <= 2){
            throw new LocationNameIsInvalidException();
        }
    }
    public void validateOptional(Optional<Location> optional){
        if(optional.isEmpty()){
            throw new LocationNotFoundException();
        }
    }
}
