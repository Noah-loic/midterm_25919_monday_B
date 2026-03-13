package auca.ac.rw.restfullApiAssignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import auca.ac.rw.restfullApiAssignment.modal.StudentEntity;
import auca.ac.rw.restfullApiAssignment.modal.CourseEntity;
import auca.ac.rw.restfullApiAssignment.service.StudentService;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveStudent(@RequestBody StudentEntity student) {
        String result = studentService.saveStudent(student);
        if (result.equals("Student saved successfully")) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(result, HttpStatus.CONFLICT);
    }
    
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StudentEntity>> getAllStudents() {
        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
    }
    
    @GetMapping(value = "/paginated", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<StudentEntity>> getAllStudentsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "studentId") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction) {
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        return new ResponseEntity<>(studentService.getAllStudents(pageable), HttpStatus.OK);
    }
    
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getStudentById(@PathVariable String id) {
        StudentEntity student = studentService.getStudentById(id);
        if (student != null) {
            return new ResponseEntity<>(student, HttpStatus.OK);
        }
        return new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND);
    }
    
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateStudent(@PathVariable String id, @RequestBody StudentEntity student) {
        String result = studentService.updateStudent(id, student);
        if (result.equals("Student updated successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }
    
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteStudent(@PathVariable String id) {
        String result = studentService.deleteStudent(id);
        if (result.equals("Student deleted successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }
    
    @GetMapping(value = "/province/code/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StudentEntity>> getStudentsByProvinceCode(@PathVariable String code) {
        return new ResponseEntity<>(studentService.getStudentsByProvinceCode(code), HttpStatus.OK);
    }
    
    @GetMapping(value = "/province/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StudentEntity>> getStudentsByProvinceName(@PathVariable String name) {
        return new ResponseEntity<>(studentService.getStudentsByProvinceName(name), HttpStatus.OK);
    }
    
    @GetMapping(value = "/location/code/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StudentEntity>> getStudentsByLocationCode(@PathVariable String code) {
        return new ResponseEntity<>(studentService.getStudentsByLocationCode(code), HttpStatus.OK);
    }
    
    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StudentEntity>> searchStudentsByName(@RequestParam String name) {
        return new ResponseEntity<>(studentService.searchByName(name), HttpStatus.OK);
    }
    
    @GetMapping(value = "/{studentId}/courses", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CourseEntity>> getCoursesByStudent(@PathVariable String studentId) {
        return new ResponseEntity<>(studentService.getCoursesByStudent(studentId), HttpStatus.OK);
    }
}
