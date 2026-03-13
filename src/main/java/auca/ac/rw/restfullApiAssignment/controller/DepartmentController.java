package auca.ac.rw.restfullApiAssignment.controller;

import auca.ac.rw.restfullApiAssignment.modal.Department;
import auca.ac.rw.restfullApiAssignment.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    
    @Autowired
    private DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Department department) {
        String result = departmentService.save(department);
        return result.equals("Department saved successfully") 
            ? new ResponseEntity<>(result, HttpStatus.CREATED)
            : new ResponseEntity<>(result, HttpStatus.CONFLICT);
    }

    @GetMapping
    public ResponseEntity<List<Department>> findAll() {
        return new ResponseEntity<>(departmentService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        Department department = departmentService.findById(id);
        return department != null 
            ? new ResponseEntity<>(department, HttpStatus.OK)
            : new ResponseEntity<>("Department not found", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Department department) {
        String result = departmentService.update(id, department);
        return result.equals("Department updated successfully")
            ? new ResponseEntity<>(result, HttpStatus.OK)
            : new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        String result = departmentService.delete(id);
        return result.equals("Department deleted successfully")
            ? new ResponseEntity<>(result, HttpStatus.OK)
            : new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Department>> searchByName(@RequestParam String name) {
        return new ResponseEntity<>(departmentService.searchByName(name), HttpStatus.OK);
    }
}
