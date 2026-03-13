package auca.ac.rw.restfullApiAssignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import auca.ac.rw.restfullApiAssignment.modal.*;
import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Integer> {
    List<Registration> findByStudent(StudentEntity student);
    List<Registration> findByCourse(CourseEntity course);
    List<Registration> findByStatus(ERegistrationStatus status);
    Optional<Registration> findByStudentAndCourse(StudentEntity student, CourseEntity course);
    Boolean existsByStudentAndCourse(StudentEntity student, CourseEntity course);
    List<Registration> findByCourseCourseCode(String courseCode);
}
