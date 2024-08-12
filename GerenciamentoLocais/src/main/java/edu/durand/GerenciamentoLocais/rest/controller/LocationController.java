package edu.durand.GerenciamentoLocais.rest.controller;

import edu.durand.GerenciamentoLocais.application.dto.LocationDTO;
import edu.durand.GerenciamentoLocais.application.service.LocationService;
import edu.durand.GerenciamentoLocais.domain.model.Location;
import edu.durand.GerenciamentoLocais.rest.api.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.type.NullType;
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
    public ResponseEntity<ApiResponse<Location>> postLocation(@RequestBody LocationDTO request)
            throws IOException {
        var response = locationService.createLocation(request);
        ApiResponse<Location> apiResponse = new ApiResponse<>("Lugar criado com sucesso!", response);
        return ResponseEntity.ok(apiResponse);
    }
    @Operation(summary = "Recupera todos os locais em ordem de criação", method = "GET")
    @GetMapping()
    public ResponseEntity<ApiResponse<List<Location>>> getAllByCreationDate(){
        var response = locationService.getAllByCreationOrder();
        ApiResponse<List<Location>> apiResponse = new ApiResponse<>("Lugares por ordem de criação:", response);
        return ResponseEntity.ok(apiResponse);
    }
    @Operation(summary = "Recupera todos os locais por ordem de mais recentes", method = "GET")
    @GetMapping("/recent")
    public ResponseEntity<ApiResponse<List<Location>>> getAllByRecentCreation(){
        var response = locationService.getAllByRecentCreation();
        ApiResponse<List<Location>> apiResponse = new ApiResponse<>("Lugares criados mais recentemente:", response);
        return ResponseEntity.ok(apiResponse);
    }
    @Operation(summary = "Atualiza um local pelo ID", method = "PUT")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Location>> updateLocation(@PathVariable("id") long id, @RequestBody LocationDTO location) throws IOException {
        var response = this.locationService.updateLocation(id, location);
        ApiResponse<Location> apiResponse = new ApiResponse<>("Lugar atualizado com sucesso", response);
        return ResponseEntity.ok(apiResponse);
    }
    @Operation(summary = "Deleta um local pelo ID", method = "DELETE")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteLocation(@PathVariable("id") long id){
        locationService.deleteLocation(id);
        ApiResponse<NullType> apiResponse = new ApiResponse<>("Lugar deletado com sucesso", null);
        return ResponseEntity.ok(apiResponse);
    }
}
