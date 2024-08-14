package edu.durand.GerenciamentoLocais.application.service.contract;

import edu.durand.GerenciamentoLocais.application.dto.LocationDTO;
import edu.durand.GerenciamentoLocais.domain.model.Location;

import java.io.IOException;
import java.util.List;

public interface LocationService {
    Location createLocation(LocationDTO request) throws IOException;
    List<Location> getAll();
    List<Location> getAllByCreationOrder();
    List<Location> getAllByRecentCreation();
    Location getById(long id);
    Location updateLocation(long id, LocationDTO update) throws IOException;
    void deleteLocation(long id);

}
