package edu.durand.GerenciamentoLocais.application.service;

import edu.durand.GerenciamentoLocais.domain.model.Location;
import edu.durand.GerenciamentoLocais.domain.repository.LocationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class LocationService {
    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }
    public void createLocation(Location request){
        Location location = new Location(request.getName(), request.getAddress());
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
        Optional<Location> optional = locationRepository.findById(id);
        if(optional.isPresent()){
            locationRepository.deleteById(id);
        } else {
            System.out.println("Local nao encontrado");
        }
    }
}
