package auca.ac.rw.restfullApiAssignment.controller;

import auca.ac.rw.restfullApiAssignment.modal.Instructor;
import auca.ac.rw.restfullApiAssignment.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/instructors")
public class InstructorController {
    
    @Autowired
    private InstructorService instructorService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Instructor instructor) {
        return ResponseEntity.ok(instructorService.save(instructor));
    }

    @GetMapping
    public ResponseEntity<List<Instructor>> getAll() {
        return ResponseEntity.ok(instructorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instructor> getById(@PathVariable String id) {
        Instructor instructor = instructorService.findById(id);
        return instructor != null ? ResponseEntity.ok(instructor) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody Instructor instructor) {
        return ResponseEntity.ok(instructorService.update(id, instructor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        return ResponseEntity.ok(instructorService.delete(id));
    }
}
