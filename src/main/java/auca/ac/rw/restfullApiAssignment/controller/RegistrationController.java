package auca.ac.rw.restfullApiAssignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import auca.ac.rw.restfullApiAssignment.modal.Registration;
import auca.ac.rw.restfullApiAssignment.service.RegistrationService;
import java.util.List;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {
    
    @Autowired
    private RegistrationService registrationService;
    
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerStudent(@RequestBody Registration registration) {
        String result = registrationService.registerStudent(
            registration.getStudent().getStudentId(), 
            registration.getCourse().getCourseCode()
        );
        if (result.equals("Registration successful")) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping(value = "/student/{studentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Registration>> getStudentRegistrations(@PathVariable String studentId) {
        return new ResponseEntity<>(registrationService.getStudentRegistrations(studentId), HttpStatus.OK);
    }
    
    @GetMapping(value = "/course/{courseCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Registration>> getCourseRegistrations(@PathVariable String courseCode) {
        return new ResponseEntity<>(registrationService.getCourseRegistrations(courseCode), HttpStatus.OK);
    }
    
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> dropRegistration(@PathVariable Integer id) {
        String result = registrationService.dropRegistration(id);
        if (result.equals("Registration dropped successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }
    
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateRegistration(@PathVariable Integer id, @RequestBody Registration registration) {
        String result = registrationService.updateRegistration(id, registration.getStatus(), registration.getGrade());
        if (result.equals("Registration updated successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }
}
