package edu.durand.GerenciamentoLocais.rest.controller;

import edu.durand.GerenciamentoLocais.application.dto.CreateLocalDTO;
import edu.durand.GerenciamentoLocais.application.service.LocationService;
import edu.durand.GerenciamentoLocais.domain.model.Location;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping("/new")
    public void postLocation(@RequestBody CreateLocalDTO request) throws IOException {
        locationService.createLocation(request);
    }
    @GetMapping()
    public ResponseEntity<List<Location>> getAllByCreationDate(){
        return locationService.getAllByCreationOrder();
    }
    @GetMapping("/recents")
    public ResponseEntity<List<Location>> getAllByRecentCreation(){
        return locationService.getAllByRecentCreation();
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Location> updateLocation(@PathVariable("id") long id, Location location){
        return locationService.updateLocation(id, location);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteLocation(@PathVariable("id") long id){
        locationService.deleteLocation(id);
    }
}
