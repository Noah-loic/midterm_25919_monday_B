package auca.ac.rw.restfullApiAssignment.repository;

import auca.ac.rw.restfullApiAssignment.modal.ELocationType;
import auca.ac.rw.restfullApiAssignment.modal.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LocationRepository extends JpaRepository<Location, UUID> {
    Optional<Location> findByCode(String code);
    Optional<Location> findByName(String name);
    List<Location> findByType(ELocationType type);
    boolean existsByCode(String code);
}
