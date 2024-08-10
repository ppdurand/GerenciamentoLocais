package edu.durand.GerenciamentoLocais.application.service;

import edu.durand.GerenciamentoLocais.domain.model.Location;
import edu.durand.GerenciamentoLocais.domain.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {
    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }
    public void createLocation(Location location){
        locationRepository.save(location);
    }
    public ResponseEntity<List<Location>> getAllByCreationOrder(){
        return ResponseEntity.ok().body(locationRepository.findAll());
    }
    public ResponseEntity<Location> updateLocation(long id, Location location){
        Optional<Location> optional = locationRepository.findById(id);
        if (optional.isPresent()) {
            Location existingLocation = optional.get();

            existingLocation.setName(location.getName());
            existingLocation.setAddress(location.getAddress());

            locationRepository.save(existingLocation);

            return ResponseEntity.ok().body(existingLocation);
        } else {
            return ResponseEntity.badRequest().body(optional.get());
        }
    }
    public void deleteLocation(long id) {
        locationRepository.deleteById(id);
    }
}
