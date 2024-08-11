package edu.durand.GerenciamentoLocais.application.mapper;

import edu.durand.GerenciamentoLocais.application.dto.LocationDTO;
import edu.durand.GerenciamentoLocais.domain.model.Address;
import edu.durand.GerenciamentoLocais.domain.model.Location;
import edu.durand.GerenciamentoLocais.infra.client.ViaCepClient;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LocationMapper {
    private final ViaCepClient client;

    public LocationMapper(ViaCepClient client) {
        this.client = client;
    }

    public Location toModel(LocationDTO locationDTO, Location model) throws IOException {
        Address address = client.cepConsult(locationDTO.cep());
        model.setName(locationDTO.name());
        model.setAddress(new Address(address.getCep(), address.getUf(), address.getLocalidade(),
                address.getBairro(), address.getLogradouro(), locationDTO.number(), locationDTO.complement()));
        return model;

    }

    public LocationDTO toDTO(Location location){
        return new LocationDTO(location.getName(), location.getAddress().getCep(),
                location.getAddress().getNumero(), location.getAddress().getComplemento());
    }
}
