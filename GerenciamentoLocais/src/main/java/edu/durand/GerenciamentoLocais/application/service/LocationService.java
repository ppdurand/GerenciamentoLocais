package edu.durand.GerenciamentoLocais.application.service;

import edu.durand.GerenciamentoLocais.application.dto.LocationDTO;
import edu.durand.GerenciamentoLocais.application.mapper.LocationMapper;
import edu.durand.GerenciamentoLocais.domain.model.Address;
import edu.durand.GerenciamentoLocais.domain.model.Location;
import edu.durand.GerenciamentoLocais.domain.repository.LocationRepository;
import edu.durand.GerenciamentoLocais.infra.client.ViaCepClient;
import edu.durand.GerenciamentoLocais.rest.exception.LocationNotFoundException;
import edu.durand.GerenciamentoLocais.rest.validation.LocationValidator;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper mapper;
    private final ViaCepClient client;
    private final LocationValidator validator;

    public LocationService(LocationRepository locationRepository, LocationMapper mapper, ViaCepClient client, LocationValidator validator) {
        this.locationRepository = locationRepository;
        this.mapper = mapper;
        this.client = client;
        this.validator = validator;
    }

    public Location createLocation(LocationDTO request) throws IOException {
        this.validator.validateLocation(request);

        Address address = client.cepConsult(request.cep());
        address.setNumero(request.number());
        address.setComplemento(request.complement());

        Location location = new Location(request.name(), address);
        locationRepository.save(location);
        return location;
    }

    public List<Location> getAll(){
        return locationRepository.findAll();
    }
    public List<Location> getAllByCreationOrder(){
        List<Location> allLocations = getAll();
        allLocations.sort(Comparator.comparing(Location::getCreationDate));

        return allLocations;
    }

    public List<Location> getAllByRecentCreation(){
        List<Location> allLocations = getAll();
        allLocations.sort(Comparator.comparing(Location::getCreationDate).reversed());
        return allLocations;
    }

    public Location getById(long id){
        Optional<Location> optional = this.locationRepository.findById(id);
        this.validator.validateOptional(optional);
        return optional.get();
    }

    public Location updateLocation(long id, LocationDTO update) throws IOException {
        this.validator.validateLocation(update);
        Optional<Location> optional = locationRepository.findById(id);

        if (optional.isEmpty()) {
            throw new LocationNotFoundException();
        }
        Location location = mapper.toModel(update, optional.get());
        location.setUpdateDate(LocalDateTime.now());
        this.locationRepository.save(location);

        return location;
    }

    public void deleteLocation(long id) {
        Optional<Location> optional = locationRepository.findById(id);
        if(optional.isPresent()){
            locationRepository.deleteById(id);
        } else {
            throw new LocationNotFoundException();
        }
    }
}
