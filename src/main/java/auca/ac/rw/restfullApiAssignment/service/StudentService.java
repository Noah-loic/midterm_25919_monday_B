package auca.ac.rw.restfullApiAssignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import auca.ac.rw.restfullApiAssignment.modal.StudentEntity;
import auca.ac.rw.restfullApiAssignment.modal.CourseEntity;
import auca.ac.rw.restfullApiAssignment.modal.Registration;
import auca.ac.rw.restfullApiAssignment.modal.Location;
import auca.ac.rw.restfullApiAssignment.repository.StudentRepository;
import auca.ac.rw.restfullApiAssignment.repository.LocationRepository;
import auca.ac.rw.restfullApiAssignment.repository.RegistrationRepository;
import java.util.List;

@Service
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private LocationRepository locationRepository;
    
    @Autowired
    private RegistrationRepository registrationRepository;
    
    public String saveStudent(StudentEntity student) {
        if (studentRepository.existsById(student.getStudentId())) {
            return "Student ID already exists";
        }
        if (studentRepository.existsByEmail(student.getEmail())) {
            return "Email already exists";
        }
        if (student.getLocation() != null && student.getLocation().getCode() != null) {
            Location location = locationRepository.findByCode(student.getLocation().getCode())
                .orElseThrow(() -> new RuntimeException("Location not found"));
            student.setLocation(location);
        }
        studentRepository.save(student);
        return "Student saved successfully";
    }
    
    public List<StudentEntity> getAllStudents() {
        return studentRepository.findAll();
    }
    
    public Page<StudentEntity> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }
    
    public StudentEntity getStudentById(String studentId) {
        return studentRepository.findById(studentId).orElse(null);
    }
    
    public String updateStudent(String studentId, StudentEntity student) {
        if (!studentRepository.existsById(studentId)) {
            return "Student not found";
        }
        if (student.getLocation() != null && student.getLocation().getCode() != null) {
            Location location = locationRepository.findByCode(student.getLocation().getCode())
                .orElseThrow(() -> new RuntimeException("Location not found"));
            student.setLocation(location);
        }
        student.setStudentId(studentId);
        studentRepository.save(student);
        return "Student updated successfully";
    }
    
    public String deleteStudent(String studentId) {
        if (!studentRepository.existsById(studentId)) {
            return "Student not found";
        }
        studentRepository.deleteById(studentId);
        return "Student deleted successfully";
    }
    
    public List<StudentEntity> getStudentsByProvinceCode(String provinceCode) {
        return studentRepository.findByProvinceCode(provinceCode);
    }
    
    public List<StudentEntity> getStudentsByProvinceName(String provinceName) {
        return studentRepository.findByProvinceName(provinceName);
    }
    
    public List<StudentEntity> getStudentsByLocationCode(String locationCode) {
        return studentRepository.findByLocationCode(locationCode);
    }
    
    public List<StudentEntity> searchByName(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name);
    }
    
    public List<CourseEntity> getCoursesByStudent(String studentId) {
        StudentEntity student = studentRepository.findById(studentId).orElse(null);
        if (student == null) return List.of();
        return registrationRepository.findByStudent(student)
            .stream()
            .map(Registration::getCourse)
            .toList();
    }
}
