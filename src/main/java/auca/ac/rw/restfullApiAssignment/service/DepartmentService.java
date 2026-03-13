package auca.ac.rw.restfullApiAssignment.service;

import auca.ac.rw.restfullApiAssignment.modal.Department;
import auca.ac.rw.restfullApiAssignment.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DepartmentService {
    
    @Autowired
    private DepartmentRepository departmentRepository;

    public String save(Department department) {
        if (departmentRepository.existsById(department.getDeptId())) {
            return "Department ID already exists";
        }
        departmentRepository.save(department);
        return "Department saved successfully";
    }

    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    public Department findById(String id) {
        return departmentRepository.findById(id).orElse(null);
    }

    public String update(String id, Department department) {
        if (!departmentRepository.existsById(id)) {
            return "Department not found";
        }
        department.setDeptId(id);
        departmentRepository.save(department);
        return "Department updated successfully";
    }

    public String delete(String id) {
        if (!departmentRepository.existsById(id)) {
            return "Department not found";
        }
        try {
            departmentRepository.deleteById(id);
            return "Department deleted successfully";
        } catch (Exception e) {
            return "Cannot delete department: It has associated instructors";
        }
    }
    
    public List<Department> searchByName(String name) {
        return departmentRepository.findByDeptNameContainingIgnoreCase(name);
    }
}
