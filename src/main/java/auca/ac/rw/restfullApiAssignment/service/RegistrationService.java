package auca.ac.rw.restfullApiAssignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import auca.ac.rw.restfullApiAssignment.modal.*;
import auca.ac.rw.restfullApiAssignment.repository.*;
import java.time.LocalDate;
import java.util.List;

@Service
public class RegistrationService {
    
    @Autowired
    private RegistrationRepository registrationRepository;
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private WaitlistRepository waitlistRepository;
    
    public String registerStudent(String studentId, String courseCode) {
        CourseEntity course = courseRepository.findById(courseCode).orElse(null);
        if (course == null) return "Course not found";
        
        StudentEntity student = studentRepository.findById(studentId).orElse(null);
        if (student == null) return "Student not found";
        
        if (registrationRepository.existsByStudentAndCourse(student, course)) {
            return "Already registered for this course";
        }
        
        // Check credit limits
        int currentCredits = registrationRepository.findByStudent(student).stream()
            .filter(r -> r.getStatus() == ERegistrationStatus.ACTIVE)
            .mapToInt(r -> r.getCourse().getCredits())
            .sum();
        
        if (currentCredits + course.getCredits() > student.getMaxCredits()) {
            return "Exceeds maximum credit limit";
        }
        
        // Check capacity
        if (course.isFull()) {
            if (waitlistRepository.existsByStudentAndCourse(student, course)) {
                return "Already on waitlist";
            }
            int position = waitlistRepository.findByCourseOrderByPosition(course).size() + 1;
            Waitlist waitlist = new Waitlist();
            waitlist.setStudent(student);
            waitlist.setCourse(course);
            waitlist.setPosition(position);
            waitlistRepository.save(waitlist);
            return "Added to waitlist at position " + position;
        }
        
        Registration registration = new Registration();
        registration.setStudent(student);
        registration.setCourse(course);
        registration.setRegistrationDate(LocalDate.now());
        registration.setStatus(ERegistrationStatus.ACTIVE);
        registrationRepository.save(registration);
        
        course.setEnrolledCount(course.getEnrolledCount() + 1);
        courseRepository.save(course);
        
        return "Registration successful";
    }
    
    public List<Registration> getStudentRegistrations(String studentId) {
        StudentEntity student = studentRepository.findById(studentId).orElse(null);
        return student != null ? registrationRepository.findByStudent(student) : List.of();
    }
    
    public List<Registration> getCourseRegistrations(String courseCode) {
        CourseEntity course = courseRepository.findById(courseCode).orElse(null);
        return course != null ? registrationRepository.findByCourse(course) : List.of();
    }
    
    public String dropRegistration(Integer registrationId) {
        Registration registration = registrationRepository.findById(registrationId).orElse(null);
        if (registration == null) return "Registration not found";
        
        registration.setStatus(ERegistrationStatus.DROPPED);
        registrationRepository.save(registration);
        
        CourseEntity course = registration.getCourse();
        if (course != null) {
            course.setEnrolledCount(Math.max(0, course.getEnrolledCount() - 1));
            courseRepository.save(course);
        }
        
        return "Registration dropped successfully";
    }
    
    public String updateRegistration(Integer registrationId, ERegistrationStatus status, String grade) {
        Registration registration = registrationRepository.findById(registrationId).orElse(null);
        if (registration == null) return "Registration not found";
        
        registration.setStatus(status);
        if (grade != null) registration.setGrade(grade);
        registrationRepository.save(registration);
        
        return "Registration updated successfully";
    }
}
