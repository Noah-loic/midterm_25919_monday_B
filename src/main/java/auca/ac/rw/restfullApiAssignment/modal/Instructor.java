package auca.ac.rw.restfullApiAssignment.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;




@Entity
@Table(name = "instructors")
public class Instructor {
    @Id
    @Column(name = "instructor_id", length = 20)
    private String instructorId;
    
    @Column(nullable = false)
    private String name;
    
    @Column(unique = true, nullable = false)
    private String email;
    @Column(length = 15)
    private String phone;
    
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    
    @JsonIgnore
    @OneToOne(mappedBy = "instructor")
    private User user;
    
    @JsonIgnore
    @OneToMany(mappedBy = "instructor")
    private List<CourseEntity> courses;
    
    private String specialization;
    
    @Column(name = "office_hours")
    private String officeHours;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public String getInstructorId() { return instructorId; }
    public void setInstructorId(String instructorId) { this.instructorId = instructorId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public List<CourseEntity> getCourses() { return courses; }
    public void setCourses(List<CourseEntity> courses) { this.courses = courses; }
    
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    
    public String getOfficeHours() { return officeHours; }
    public void setOfficeHours(String officeHours) { this.officeHours = officeHours; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
