package edu.durand.GerenciamentoLocais.application.service;

import edu.durand.GerenciamentoLocais.application.dto.CreateLocalDTO;
import edu.durand.GerenciamentoLocais.domain.model.Address;
import edu.durand.GerenciamentoLocais.domain.model.Location;
import edu.durand.GerenciamentoLocais.domain.repository.LocationRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
class LocationServiceTest {

    @Autowired
    private LocationService locationService;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Should create location sucessfully when everything is OK")
    void createLocationCase1() throws IOException {
        locationRepository.deleteAll();
        CreateLocalDTO location = new CreateLocalDTO("IFCE", "60040531", "2081", "");

        this.locationService.createLocation(location);

        List<Location> result = this.locationService.getAll();

        assertThat(result.isEmpty()).isFalse();
    }
//    @Test
//    @DisplayName("Should not create location if CEP is missing")
//    void createLocationCase2(){
//        locationRepository.deleteAll();
//        CreateLocalDTO location = new CreateLocalDTO("IFCE", "")
//    }

    @Test
    @DisplayName("Should get all locations in order of creation")
    void getAllByCreationOrderCase1() {
        Address adress1 = new Address("000000-001", "Ceára", "Fortaleza", "Benfica",
                "1", "25", "B");
        Location location1 = new Location("IFCE", adress1);
        location1.setCreationDate(LocalDateTime.of(2024, 8, 9, 20, 00));
        this.locationRepository.save(location1);

        Address adress2 = new Address("000000-002", "Ceára", "Fortaleza", "Pici",
                "2", "15", "Lado impar");
        Location location2 = new Location("UFC", adress2);
        location2.setCreationDate(LocalDateTime.of(2024, 8, 10, 20, 00));
        this.locationRepository.save(location2);

        ResponseEntity<List<Location>> allLocations = this.locationService.getAllByCreationOrder();

        assertThat(allLocations.getBody()).hasSize(2);
        assertThat(allLocations.getBody().get(0).getCreationDate()).isBefore(allLocations.getBody().get(1).getCreationDate());


    }

//    @Test
//    void updateLocationCase1() {
//        locationRepository.deleteAll();
//        Address address = new Address("000000-001", "Ceará", "Fortaleza",
//                "Benfica" , "1", "25", "B");
//        Location location = new Location("Lugar hipotético", address);
//        LocalDateTime creationDate = location.getCreationDate();
//        this.createLocation(location);
//
//        Address updateAddress = new Address("000000-002", "Ceará", "Fortaleza",
//                "Benfica" , "1", "25", "B");
//        Location updatedLocation = new Location("Lugar real", address);
//        updatedLocation.setCreationDate(creationDate);
//
//        ResponseEntity<Location> newLocation = this.updateLocation(location.getId(), updatedLocation);
//        Location result = newLocation.getBody();
//
//        assertThat(result).isNotNull();
//        assertThat(result.getName()).isEqualTo(updatedLocation.getName());
//        assertThat(result.getAddress()).isEqualTo(updatedLocation.getAddress());
//        assertThat(result.getCreationDate()).isEqualTo(creationDate);
//
//    }

    @Test
    @DisplayName("Should delete one location by ID")
    void deleteLocationCase1() {
        locationRepository.deleteAll();
        Address address = new Address("000000-001", "Ceará", "Fortaleza",
                "Benfica", "1", "25", "B");
        Location location = new Location("Lugar hipotético", address);

        long locationId = location.getId();

        this.locationService.deleteLocation(locationId);

        Optional<Location> result = locationRepository.findById(locationId);
        assertThat(result.isEmpty()).isTrue();
    }
}