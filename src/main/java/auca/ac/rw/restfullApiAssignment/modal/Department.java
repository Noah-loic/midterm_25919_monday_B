package auca.ac.rw.restfullApiAssignment.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "departments")
public class Department {
    
    @Id
    @Column(name = "dept_id", length = 20)
    private String deptId;
    
    @Column(name = "dept_name", nullable = false)
    private String deptName;
    
    @Column(name = "office_location")
    private String officeLocation;
    
    @JsonIgnore
    @OneToMany(mappedBy = "department")
    private List<Instructor> instructors;
    
    @JsonIgnore
    @OneToMany(mappedBy = "department")
    private List<CourseEntity> courses;

    public String getDeptId() { return deptId; }
    public void setDeptId(String deptId) { this.deptId = deptId; }
    
    public String getDeptName() { return deptName; }
    public void setDeptName(String deptName) { this.deptName = deptName; }
    
    public String getOfficeLocation() { return officeLocation; }
    public void setOfficeLocation(String officeLocation) { this.officeLocation = officeLocation; }
    
    public List<Instructor> getInstructors() { return instructors; }
    public void setInstructors(List<Instructor> instructors) { this.instructors = instructors; }
    
    public List<CourseEntity> getCourses() { return courses; }
    public void setCourses(List<CourseEntity> courses) { this.courses = courses; }
}
