package auca.ac.rw.restfullApiAssignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import auca.ac.rw.restfullApiAssignment.modal.Instructor;
import java.util.List;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, String> {
    List<Instructor> findByDepartment(String department);
    Boolean existsByEmail(String email);
    List<Instructor> findByNameContainingIgnoreCase(String name);
}
