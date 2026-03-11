package auca.ac.rw.restfullApiAssignment.repository;

import auca.ac.rw.restfullApiAssignment.modal.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {
    boolean existsByDeptName(String deptName);
}
