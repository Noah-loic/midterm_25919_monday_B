package auca.ac.rw.restfullApiAssignment.service;

import auca.ac.rw.restfullApiAssignment.modal.ELocationType;
import auca.ac.rw.restfullApiAssignment.modal.Location;
import auca.ac.rw.restfullApiAssignment.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class LocationService {
    
    @Autowired
    private LocationRepository locationRepository;

    public Location save(Location location) {
        return locationRepository.save(location);
    }

    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    public Page<Location> findAll(Pageable pageable) {
        return locationRepository.findAll(pageable);
    }

    public Location findById(UUID id) {
        return locationRepository.findById(id).orElse(null);
    }

    public Location findByCode(String code) {
        return locationRepository.findByCode(code).orElse(null);
    }

    public Location findByName(String name) {
        return locationRepository.findByName(name).orElse(null);
    }

    public List<Location> findByType(ELocationType type) {
        return locationRepository.findByType(type);
    }

    public boolean existsByCode(String code) {
        return locationRepository.existsByCode(code);
    }

    public boolean deleteByCode(String code) {
        Location location = locationRepository.findByCode(code).orElse(null);
        if (location != null) {
            locationRepository.delete(location);
            return true;
        }
        return false;
    }
}
