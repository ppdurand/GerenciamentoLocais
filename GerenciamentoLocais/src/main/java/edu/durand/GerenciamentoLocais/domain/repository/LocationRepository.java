package edu.durand.GerenciamentoLocais.domain.repository;

import edu.durand.GerenciamentoLocais.domain.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
