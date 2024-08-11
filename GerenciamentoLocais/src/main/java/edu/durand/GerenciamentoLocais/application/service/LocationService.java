package edu.durand.GerenciamentoLocais.application.service;

import edu.durand.GerenciamentoLocais.application.dto.LocationDTO;
import edu.durand.GerenciamentoLocais.application.mapper.LocationMapper;
import edu.durand.GerenciamentoLocais.domain.model.Address;
import edu.durand.GerenciamentoLocais.domain.model.Location;
import edu.durand.GerenciamentoLocais.domain.repository.LocationRepository;
import edu.durand.GerenciamentoLocais.infra.client.ViaCepClient;
import edu.durand.GerenciamentoLocais.rest.exception.CepIsMissingException;
import edu.durand.GerenciamentoLocais.rest.exception.LocationNotFound;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper mapper;
    private final ViaCepClient client;

    public LocationService(LocationRepository locationRepository, LocationMapper mapper, ViaCepClient client) {
        this.locationRepository = locationRepository;
        this.mapper = mapper;
        this.client = client;
    }
    public void createLocation(LocationDTO request) throws IOException {
        if((request.cep()).isBlank() || (request.cep()).isEmpty()){
            throw new CepIsMissingException();
        }

        Address address = client.cepConsult(request.cep());
        address.setNumero(request.number());
        address.setComplemento(request.complement());

        Location location = new Location(request.name(), address);
        locationRepository.save(location);
    }
    public List<Location> getAll(){
        return locationRepository.findAll();
    }
    public ResponseEntity<List<Location>> getAllByCreationOrder(){
        List<Location> allLocations = getAll();
        allLocations.sort(Comparator.comparing(Location::getCreationDate));

        return ResponseEntity.ok().body(allLocations);
    }
    public ResponseEntity<List<Location>> getAllByRecentCreation(){
        List<Location> allLocations = getAll();
        allLocations.sort(Comparator.comparing(Location::getCreationDate).reversed());
        return ResponseEntity.ok().body(allLocations);
    }
    public ResponseEntity<Location> updateLocation(long id, LocationDTO update) throws IOException {
        if(update.cep().isBlank() || update.cep().isEmpty()){
            throw new CepIsMissingException();
        }
        Optional<Location> optional = locationRepository.findById(id);

        if (optional.isEmpty()) {
            throw new LocationNotFound();
        }
        Location location = mapper.toModel(update, optional.get());
        this.locationRepository.save(location);

        return ResponseEntity.ok().body(location);
    }
    public void deleteLocation(long id) {
        Optional<Location> optional = locationRepository.findById(id);
        if(optional.isPresent()){
            locationRepository.deleteById(id);
        } else {
            throw new LocationNotFound();
        }
    }
}
