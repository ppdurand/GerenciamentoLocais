package edu.durand.GerenciamentoLocais.rest.controller;

import edu.durand.GerenciamentoLocais.application.dto.LocationDTO;
import edu.durand.GerenciamentoLocais.application.service.contract.LocationService;
import edu.durand.GerenciamentoLocais.domain.model.Location;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/locations")
@Tag(name = "Gerenciador de Locais")
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @Operation(summary = "Realiza o cadastro de locais", method = "POST")
    @PostMapping("/new")
    public ResponseEntity<Location> postLocation(@RequestBody LocationDTO request)
            throws IOException {
        var response = locationService.createLocation(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Recupera todos os locais em ordem de criação", method = "GET")
    @GetMapping()
    public ResponseEntity<List<Location>> getAllByCreationDate(){
        var response = locationService.getAllByCreationOrder();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Recupera todos os locais por ordem de mais recentes", method = "GET")
    @GetMapping("/recent")
    public ResponseEntity<List<Location>> getAllByRecentCreation(){
        var response = locationService.getAllByRecentCreation();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Recupera um único lugar pelo ID")
    @GetMapping("/search/{id}")
    public ResponseEntity<Location> getById(@PathVariable("id") long id){
        var response = locationService.getById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualiza um local pelo ID", method = "PUT")
    @PutMapping("/update/{id}")
    public ResponseEntity<Location> updateLocation(@PathVariable("id") long id, @RequestBody LocationDTO location) throws IOException {
        var response = this.locationService.updateLocation(id, location);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Deleta um local pelo ID", method = "DELETE")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteLocation(@PathVariable("id") long id){
        locationService.deleteLocation(id);
        return ResponseEntity.ok().body("Lugar Deletado");
    }
}
