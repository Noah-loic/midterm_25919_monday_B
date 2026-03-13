package auca.ac.rw.restfullApiAssignment.service;

import auca.ac.rw.restfullApiAssignment.modal.Instructor;
import auca.ac.rw.restfullApiAssignment.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InstructorService {
    
    @Autowired
    private InstructorRepository instructorRepository;

    public String save(Instructor instructor) {
        if (instructorRepository.existsById(instructor.getInstructorId())) {
            return "Instructor ID already exists";
        }
        instructorRepository.save(instructor);
        return "Instructor saved successfully";
    }

    public List<Instructor> findAll() {
        return instructorRepository.findAll();
    }

    public Instructor findById(String id) {
        return instructorRepository.findById(id).orElse(null);
    }

    public String update(String id, Instructor instructor) {
        if (!instructorRepository.existsById(id)) {
            return "Instructor not found";
        }
        instructor.setInstructorId(id);
        instructorRepository.save(instructor);
        return "Instructor updated successfully";
    }

    public String delete(String id) {
        if (!instructorRepository.existsById(id)) {
            return "Instructor not found";
        }
        instructorRepository.deleteById(id);
        return "Instructor deleted successfully";
    }
    
    public List<Instructor> searchByName(String name) {
        return instructorRepository.findByNameContainingIgnoreCase(name);
    }
}
