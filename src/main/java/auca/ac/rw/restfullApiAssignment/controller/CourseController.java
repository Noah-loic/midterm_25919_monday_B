package auca.ac.rw.restfullApiAssignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import auca.ac.rw.restfullApiAssignment.modal.CourseEntity;
import auca.ac.rw.restfullApiAssignment.service.CourseService;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    
    @Autowired
    private CourseService courseService;
    
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveCourse(@RequestBody CourseEntity course) {
        String result = courseService.saveCourse(course);
        if (result.equals("Course saved successfully")) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(result, HttpStatus.CONFLICT);
    }
    
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CourseEntity>> getAllCourses() {
        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
    }
    
    @GetMapping(value = "/paginated", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<CourseEntity>> getAllCoursesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "courseCode") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction) {
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        return new ResponseEntity<>(courseService.getAllCourses(pageable), HttpStatus.OK);
    }
    
    @GetMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCourseById(@PathVariable String code) {
        CourseEntity course = courseService.getCourseById(code);
        if (course != null) {
            return new ResponseEntity<>(course, HttpStatus.OK);
        }
        return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
    }
    
    @GetMapping(value = "/department/{department}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CourseEntity>> getCoursesByDepartment(@PathVariable String department) {
        return new ResponseEntity<>(courseService.getCoursesByDepartment(department), HttpStatus.OK);
    }
    
    @GetMapping(value = "/instructor/{instructorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CourseEntity>> getCoursesByInstructor(@PathVariable String instructorId) {
        return new ResponseEntity<>(courseService.getCoursesByInstructor(instructorId), HttpStatus.OK);
    }
    
    @PutMapping(value = "/{code}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCourse(@PathVariable String code, @RequestBody CourseEntity course) {
        String result = courseService.updateCourse(code, course);
        if (result.equals("Course updated successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }
    
    @DeleteMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteCourse(@PathVariable String code) {
        String result = courseService.deleteCourse(code);
        if (result.equals("Course deleted successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }
    
    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CourseEntity>> searchCoursesByTitle(@RequestParam String name) {
        return new ResponseEntity<>(courseService.searchByTitle(name), HttpStatus.OK);
    }
}
