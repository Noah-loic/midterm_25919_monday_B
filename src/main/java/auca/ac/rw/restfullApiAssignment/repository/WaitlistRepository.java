package auca.ac.rw.restfullApiAssignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import auca.ac.rw.restfullApiAssignment.modal.*;
import java.util.List;

@Repository
public interface WaitlistRepository extends JpaRepository<Waitlist, Integer> {
    List<Waitlist> findByCourseOrderByPosition(CourseEntity course);
    Boolean existsByStudentAndCourse(StudentEntity student, CourseEntity course);
}
