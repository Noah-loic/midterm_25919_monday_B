package auca.ac.rw.restfullApiAssignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import auca.ac.rw.restfullApiAssignment.modal.CourseEntity;
import auca.ac.rw.restfullApiAssignment.repository.CourseRepository;
import java.util.List;

@Service
public class CourseService {
    
    @Autowired
    private CourseRepository courseRepository;
    
    public String saveCourse(CourseEntity course) {
        if (courseRepository.existsById(course.getCourseCode())) {
            return "Course code already exists";
        }
        courseRepository.save(course);
        return "Course saved successfully";
    }
    
    public List<CourseEntity> getAllCourses() {
        return courseRepository.findAll();
    }
    
    public Page<CourseEntity> getAllCourses(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }
    
    public CourseEntity getCourseById(String courseCode) {
        return courseRepository.findById(courseCode).orElse(null);
    }
    
    public List<CourseEntity> getCoursesByDepartment(String department) {
        return courseRepository.findByDepartment(department);
    }
    
    public List<CourseEntity> getCoursesByInstructor(String instructorId) {
        return courseRepository.findByInstructorInstructorId(instructorId);
    }
    
    public String updateCourse(String courseCode, CourseEntity course) {
        if (!courseRepository.existsById(courseCode)) {
            return "Course not found";
        }
        course.setCourseCode(courseCode);
        courseRepository.save(course);
        return "Course updated successfully";
    }
    
    public String deleteCourse(String courseCode) {
        if (!courseRepository.existsById(courseCode)) {
            return "Course not found";
        }
        courseRepository.deleteById(courseCode);
        return "Course deleted successfully";
    }
    
    public List<CourseEntity> searchByTitle(String title) {
        return courseRepository.findByTitleContainingIgnoreCase(title);
    }
}
