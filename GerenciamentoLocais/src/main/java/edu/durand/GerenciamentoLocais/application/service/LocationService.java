package edu.durand.GerenciamentoLocais.application.service;

import com.google.gson.Gson;
import edu.durand.GerenciamentoLocais.application.dto.CreateLocalDTO;
import edu.durand.GerenciamentoLocais.domain.model.Address;
import edu.durand.GerenciamentoLocais.domain.model.Location;
import edu.durand.GerenciamentoLocais.domain.repository.LocationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLOutput;
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
    public void createLocation(CreateLocalDTO request) throws IOException {
        //Consultando enndereço via CEP

        Address address = this.cepConsult(request.cep());
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
    //Consultando enndereço via CEP
    public Address cepConsult(String cep) throws IOException {
        URL url = new URL("https://viacep.com.br/ws/"+ cep +"/json/");

        URLConnection connection = url.openConnection();
        InputStream inputStream = connection.getInputStream();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

        String responseCep = "";
        StringBuilder jsonCep = new StringBuilder();

        while((responseCep = buffer.readLine()) != null){
            jsonCep.append(responseCep);
        }

        return new Gson().fromJson(jsonCep.toString(), Address.class);
    }
}
