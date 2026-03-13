package auca.ac.rw.restfullApiAssignment.repository;

import auca.ac.rw.restfullApiAssignment.modal.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {
    boolean existsByDeptName(String deptName);
    List<Department> findByDeptNameContainingIgnoreCase(String deptName);
}
