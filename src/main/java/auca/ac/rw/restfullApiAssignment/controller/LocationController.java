package auca.ac.rw.restfullApiAssignment.controller;

import auca.ac.rw.restfullApiAssignment.modal.ELocationType;
import auca.ac.rw.restfullApiAssignment.modal.Location;
import auca.ac.rw.restfullApiAssignment.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
    
    @Autowired
    private LocationService locationService;

    @PostMapping
    public ResponseEntity<Location> save(@RequestBody Location location) {
        return ResponseEntity.ok(locationService.save(location));
    }

    @GetMapping
    public ResponseEntity<List<Location>> findAll() {
        return ResponseEntity.ok(locationService.findAll());
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<Location>> findAllPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "code") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction) {
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        return ResponseEntity.ok(locationService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> findById(@PathVariable UUID id) {
        Location location = locationService.findById(id);
        return location != null ? ResponseEntity.ok(location) : ResponseEntity.notFound().build();
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<Location> findByCode(@PathVariable String code) {
        Location location = locationService.findByCode(code);
        return location != null ? ResponseEntity.ok(location) : ResponseEntity.notFound().build();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Location> findByName(@PathVariable String name) {
        Location location = locationService.findByName(name);
        return location != null ? ResponseEntity.ok(location) : ResponseEntity.notFound().build();
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Location>> findByType(@PathVariable ELocationType type) {
        return ResponseEntity.ok(locationService.findByType(type));
    }

    @GetMapping("/exists/{code}")
    public ResponseEntity<Boolean> existsByCode(@PathVariable String code) {
        return ResponseEntity.ok(locationService.existsByCode(code));
    }

    @DeleteMapping("/code/{code}")
    public ResponseEntity<Void> deleteByCode(@PathVariable String code) {
        return locationService.deleteByCode(code) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
