package edu.durand.GerenciamentoLocais.application.service;

import edu.durand.GerenciamentoLocais.application.dto.LocationDTO;
import edu.durand.GerenciamentoLocais.domain.model.Location;
import edu.durand.GerenciamentoLocais.domain.contract.repository.LocationRepository;
import edu.durand.GerenciamentoLocais.rest.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class LocationServiceImplTest {

    @Autowired
    @InjectMocks
    private LocationServiceImpl locationServiceImpl;
    @Mock
    private LocationRepository locationRepository;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Should get all locations in order of creation")
    void shouldGetAllByrOrderOfCreation() throws IOException {
        //Arrang e
        this.locationRepository.deleteAll();
        LocationDTO location1 = new LocationDTO("Lugar 1", "24120180", "1", "lado impar");
        Location entity1 = this.locationServiceImpl.createLocation(location1);

        if(entity1 == null){
            fail("Primeiro lugar não foi criado com sucesso");
        }

        entity1.setCreationDate(LocalDateTime.of(2024, 8, 9, 20, 0));

        LocationDTO location2 = new LocationDTO("Lugar 2", "69911800", "2", "lado par");
        Location entity2 = this.locationServiceImpl.createLocation(location2);

        if(entity2 == null){
            fail("Segundo lugar não foi criado com sucesso");
        }

        entity2.setCreationDate(LocalDateTime.of(2024, 8, 10, 20, 0));

        //Act
        List<Location> allLocations = this.locationServiceImpl.getAllByCreationOrder();

        //Assert
        assertThat(allLocations).hasSize(2);
        assertThat(allLocations.get(0).getCreationDate()).isBefore(allLocations.get(1).getCreationDate());
    }
    @Test
    @DisplayName("Should get all locations by more recent order")
    void shouldGetAllByMoreRecentOrder() throws IOException {
        //Arrang e
        this.locationRepository.deleteAll();
        LocationDTO location1 = new LocationDTO("Lugar 1", "24120180", "1", "lado impar");
        Location entity1 = this.locationServiceImpl.createLocation(location1);

        if(entity1 == null){
            fail("Primeiro lugar não foi criado com sucesso");
        }

        entity1.setCreationDate(LocalDateTime.of(2024, 8, 9, 20, 0));

        LocationDTO location2 = new LocationDTO("Lugar 2", "69911800", "2", "lado par");
        Location entity2 = this.locationServiceImpl.createLocation(location2);

        if(entity2 == null){
            fail("Segundo lugar não foi criado com sucesso");
        }

        entity2.setCreationDate(LocalDateTime.of(2024, 8, 10, 20, 0));

        // Act
        List<Location> allLocations = this.locationServiceImpl.getAllByRecentCreation();

        //Assert
        assertThat(allLocations).hasSize(2);
        assertThat(allLocations.get(0).getCreationDate()).isAfter(allLocations.get(1).getCreationDate());

    }
    @Test
    @DisplayName("Should get one location by ID")
    void shouldGetLocationById() throws IOException {
        //Arrange
        this.locationRepository.deleteAll();
        LocationDTO location = new LocationDTO("Lugar 1", "24120180", "1", "lado impar");
        Location entity = this.locationServiceImpl.createLocation(location);

        if(entity == null){
            fail("Primeiro lugar não foi criado com sucesso");
        }

        //Arrange
        Location searchLocation = this.locationServiceImpl.getById(entity.getId());

        //Assert
        assertThat(searchLocation.getId()).isEqualTo(entity.getId());
        assertThat(searchLocation.getName()).isEqualTo(entity.getName());
    }
    @Test
    @DisplayName("Should create location sucessfully when everything is OK")
    void shouldCreateLocation() throws IOException {
        locationRepository.deleteAll();
        LocationDTO location = new LocationDTO("IFCE", "60040531", "2081", "");

        //Act
        this.locationServiceImpl.createLocation(location);
        List<Location> result = this.locationServiceImpl.getAll();

        assertThat(result.isEmpty()).isFalse();
    }
    @Test
    @DisplayName("Should not create location if CEP is blank")
    void shouldNotCreateWhenCepIsMissing() {
        //Arrange
        this.locationRepository.deleteAll();
        LocationDTO location = new LocationDTO("IFCE", "", "1", "");

        //Act and Assert
        Exception exception = assertThrows(CepIsMissingException.class,
                () -> this.locationServiceImpl.createLocation(location));
        assertThat(exception.getMessage()).contains("Informe o CEP");

    }
    @Test
    @DisplayName("Should not create location if CEP is null")
    void shouldNotCreatWhenCepIsNull(){
        //Arrange
        this.locationRepository.deleteAll();
        LocationDTO location = new LocationDTO("IFCE", null,"1", "");

        //Act and Assert
        Exception exception = assertThrows(CepIsNullException.class,
                ()-> this.locationServiceImpl.createLocation(location));
        assertThat(exception.getMessage()).contains("O CEP não pode ser nulo");
    }
    @Test
    @DisplayName("Should not create location when name is blank or less than 3 characters")
    void shouldNotCreatWhenNameisBlankOr3Characters(){
        //Arrange
        this.locationRepository.deleteAll();
        LocationDTO location = new LocationDTO("", "59022-305","1", "");

        //Act and Assert
        Exception exception = assertThrows(LocationNameIsInvalidException.class,
                ()-> this.locationServiceImpl.createLocation(location));
        assertThat(exception.getMessage()).contains("O nome do local precisa de no mínimo 3 caracteres");
    }

    @Test
    @DisplayName("Should not create location when name is null")
    void shouldNotCreateLocationWhenNameIsNull(){
        //Arrange
        this.locationRepository.deleteAll();
        LocationDTO location = new LocationDTO(null, "59022-305","1", "");

        //Act and Assert
        Exception exception = assertThrows(LocationNameIsNullException.class,
                () -> this.locationServiceImpl.createLocation(location));
        assertThat(exception.getMessage()).contains("O nome do local não deve ser nulo");
    }
    @Test
    @DisplayName("Should update location address, but preserve name")
    void shouldUpdateLocationAddressAndPreserveOtherFields() throws IOException {
        //Arrange
        locationRepository.deleteAll();
        LocationDTO location = new LocationDTO("IFCE", "60040531", "2081", "");
        Location entity = this.locationServiceImpl.createLocation(location);
        if(entity == null){
            fail("O lugar não foi criado com sucesso");
        }

        //Act
        LocationDTO update = new LocationDTO("IFCE", "60340325", "81", "ao lado do mercado");
        Location updateEntity = this.locationServiceImpl.updateLocation(entity.getId(), update);

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
        this.locationRepository.deleteAll();
        LocationDTO location = new LocationDTO("IFCE", "60040531", "2081", "");
        Location entity = this.locationServiceImpl.createLocation(location);
        if(entity == null){
            fail("O lugar não foi criado com sucesso");
        }

        //Act
        LocationDTO update = new LocationDTO("UFC", "60040531", "2081", "");
        Location updateEntity = this.locationServiceImpl.updateLocation(entity.getId(), update);

        if(updateEntity == null){
            fail("O lugar não foi atualizado com sucesso");
        }

        //Assert
        assertThat(updateEntity.getName()).isNotEqualTo(entity.getName());
        assertThat(updateEntity.getAddress()).isEqualTo(entity.getAddress());
    }
    @Test
    @DisplayName("should update the date when location was updated")
    void shouldUpdateDateWhenLocationWasUpdated() throws IOException {
        //Arrange
        this.locationRepository.deleteAll();
        LocationDTO location = new LocationDTO("Casa", "60040531", "123", "");
        Location entity = this.locationServiceImpl.createLocation(location);
        entity.setCreationDate(LocalDateTime.of(2024, 8, 10, 20, 0));

        //Act
        LocationDTO update = new LocationDTO("Nova casa", "58059-748",
                "321", "em frente ao mercado");
        Location updateEntity = this.locationServiceImpl.updateLocation(entity.getId(), update);

        //Assert
        assertThat(updateEntity.getUpdateDate()).isAfter(updateEntity.getCreationDate());
        assertThat(updateEntity.getUpdateDate()).isNotEqualTo(entity.getUpdateDate());

    }
    @Test
    @DisplayName("Should delete one location by ID")
    void shouldDeleteLocationById() throws IOException {
        //Arrange
        LocationDTO location = new LocationDTO("Lugar Hipotético", "60040531", "123", "");
        Location entity = this.locationServiceImpl.createLocation(location);

        //Act
        long locationId = entity.getId();
        this.locationServiceImpl.deleteLocation(locationId);

        //Assert
        Exception exception = assertThrows(LocationNotFoundException.class,
                () -> this.locationServiceImpl.deleteLocation(locationId));
        assertThat(exception.getMessage()).contains("Local Não Encontrado. Tente um ID existente");
    }
}