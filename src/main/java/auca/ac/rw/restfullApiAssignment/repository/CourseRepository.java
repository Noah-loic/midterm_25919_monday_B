package auca.ac.rw.restfullApiAssignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import auca.ac.rw.restfullApiAssignment.modal.CourseEntity;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, String> {
    List<CourseEntity> findByDepartment(String department);
    List<CourseEntity> findByInstructorInstructorId(String instructorId);
    List<CourseEntity> findByTitleContainingIgnoreCase(String title);
    List<CourseEntity> findByIsActive(Boolean isActive);
}
