package edu.durand.GerenciamentoLocais.application.service;

import edu.durand.GerenciamentoLocais.application.dto.LocationDTO;
import edu.durand.GerenciamentoLocais.domain.model.Address;
import edu.durand.GerenciamentoLocais.domain.model.Location;
import edu.durand.GerenciamentoLocais.domain.repository.LocationRepository;
import edu.durand.GerenciamentoLocais.rest.exception.CepIsMissingException;
import edu.durand.GerenciamentoLocais.rest.exception.LocationNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@SpringBootTest
@ActiveProfiles("test")
class LocationServiceTest {

    @Autowired
    @InjectMocks
    private LocationService locationService;
    @Mock
    private LocationRepository locationRepository;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Should create location sucessfully when everything is OK")
    void shouldCreateLocation() throws IOException {
        locationRepository.deleteAll();
        LocationDTO location = new LocationDTO("IFCE", "60040531", "2081", "");

        //Act
        this.locationService.createLocation(location);
        List<Location> result = this.locationService.getAll();

        assertThat(result.isEmpty()).isFalse();
    }
    @Test
    @DisplayName("Should not create location if CEP is missing")
    void shouldNotCreateWhenCepIsMissing() throws IOException {
        //Arrange
        LocationDTO location = new LocationDTO("IFCE", "", "1", "");

        //Act and Assert
        Exception exception = assertThrows(CepIsMissingException.class, () -> {
            this.locationService.createLocation(location);
        });
        assertThat(exception.getMessage()).contains("Informe o CEP");

    }

    @Test
    @DisplayName("Should get all locations in order of creation")
    void shouldGetAllByrOrderOfCreation() throws IOException {
        //Arrange
        LocationDTO location1 = new LocationDTO("Lugar 1", "24120180", "1", "lado impar");
        Location entity1 = this.locationService.createLocation(location1).getBody();

        if(entity1 == null){
            fail("Primeiro lugar não foi criado com sucesso");
        }

        entity1.setCreationDate(LocalDateTime.of(2024, 8, 9, 20, 00));

        LocationDTO location2 = new LocationDTO("Lugar 2", "69911800", "2", "lado par");
        Location entity2 = this.locationService.createLocation(location1).getBody();

        if(entity2 == null){
            fail("Segundo lugar não foi criado com sucesso");
        }

        entity2.setCreationDate(LocalDateTime.of(2024, 8, 10, 20, 00));

        //Act
        ResponseEntity<List<Location>> allLocations = this.locationService.getAllByCreationOrder();

        //Assert
        assertThat(allLocations.getBody()).hasSize(2);
        assertThat(allLocations.getBody().get(0).getCreationDate()).isBefore(allLocations.getBody().get(1).getCreationDate());
    }
    @Test
    @DisplayName("Should update location address, but preserve name")
    void shouldUpdateLocationAddressAndPreserveOtherFields() throws IOException {
        //Arrange
        locationRepository.deleteAll();
        LocationDTO location = new LocationDTO("IFCE", "60040531", "2081", "");
        Location entity = this.locationService.createLocation(location).getBody();
        if(entity == null){
            fail("O lugar não foi criado com sucesso");
        }

        //Act
        LocationDTO update = new LocationDTO("IFCE", "60340325", "81", "ao lado do mercado");
        Location updateEntity = this.locationService.updateLocation(entity.getId(), update).getBody();

        if(updateEntity == null){
            fail("O lugar não foi atualizado com sucesso");
        }

        //Assert
        assertThat(updateEntity.getName()).isEqualTo(entity.getName());
        assertThat(updateEntity.getAddress()).isNotEqualTo(entity.getAddress());
    }
    @Test
    @DisplayName("Should update location name, but preserve adress")
    void shouldUpdateLocationNameAndPreserveOtherFields() throws IOException {
        //Arrange
        LocationDTO location = new LocationDTO("IFCE", "60040531", "2081", "");
        Location entity = this.locationService.createLocation(location).getBody();
        if(entity == null){
            fail("O lugar não foi criado com sucesso");
        }

        //Act
        LocationDTO update = new LocationDTO("UFC", "60040531", "2081", "");
        Location updateEntity = this.locationService.updateLocation(entity.getId(), update).getBody();

        if(updateEntity == null){
            fail("O lugar não foi atualizado com sucesso");
        }

        //Assert
        assertThat(updateEntity.getName()).isNotEqualTo(entity.getName());
        assertThat(updateEntity.getAddress()).isEqualTo(entity.getAddress());
    }
    @Test
    @DisplayName("Should delete one location by ID")
    void shouldDeleteLocationById() throws IOException {
        //Arrange
        LocationDTO location = new LocationDTO("Lugar Hipotético", "60040531", "123", "");
        Location entity = this.locationService.createLocation(location).getBody();

        //Act
        long locationId = entity.getId();
        this.locationService.deleteLocation(locationId);

        //Assert
        Exception exception = assertThrows(LocationNotFound.class, () -> {
            this.locationService.deleteLocation(locationId);
        });
        assertThat(exception.getMessage()).contains("Local Não Encontrado. Tente um ID existente");
    }
}