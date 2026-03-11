package auca.ac.rw.restfullApiAssignment.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "courses")
public class CourseEntity {
    @Id
    @Column(name = "course_code", length = 20)
    private String courseCode;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private Integer credits;
    
    @Column(nullable = false)
    private Integer capacity;
    
    @Column(name = "enrolled_count")
    private Integer enrolledCount = 0;
    
    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;
    
    @Column(name = "schedule_time")
    private String scheduleTime;
    
    @Column(name = "schedule_days")
    private String scheduleDays;
    
    private String location;
    
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    
    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "course_prerequisites",
        joinColumns = @JoinColumn(name = "course_code"),
        inverseJoinColumns = @JoinColumn(name = "prerequisite_code")
    )
    private List<CourseEntity> prerequisites;
    
    @JsonIgnore
    @OneToMany(mappedBy = "course")
    private List<Registration> registrations;
    
    @JsonIgnore
    @OneToMany(mappedBy = "course")
    private List<Waitlist> waitlists;
    
    @Column(name = "semester_id")
    private String semesterId;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    private BigDecimal fee = BigDecimal.ZERO;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }
    
    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    
    public Integer getEnrolledCount() { return enrolledCount; }
    public void setEnrolledCount(Integer enrolledCount) { this.enrolledCount = enrolledCount; }
    
    public Instructor getInstructor() { return instructor; }
    public void setInstructor(Instructor instructor) { this.instructor = instructor; }
    
    public String getScheduleTime() { return scheduleTime; }
    public void setScheduleTime(String scheduleTime) { this.scheduleTime = scheduleTime; }
    
    public String getScheduleDays() { return scheduleDays; }
    public void setScheduleDays(String scheduleDays) { this.scheduleDays = scheduleDays; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
    
    public List<CourseEntity> getPrerequisites() { return prerequisites; }
    public void setPrerequisites(List<CourseEntity> prerequisites) { this.prerequisites = prerequisites; }
    
    public List<Registration> getRegistrations() { return registrations; }
    public void setRegistrations(List<Registration> registrations) { this.registrations = registrations; }
    
    public List<Waitlist> getWaitlists() { return waitlists; }
    public void setWaitlists(List<Waitlist> waitlists) { this.waitlists = waitlists; }
    
    public String getSemesterId() { return semesterId; }
    public void setSemesterId(String semesterId) { this.semesterId = semesterId; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public BigDecimal getFee() { return fee; }
    public void setFee(BigDecimal fee) { this.fee = fee; }
    
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public boolean isFull() { return enrolledCount >= capacity; }
    public int getAvailableSeats() { return capacity - enrolledCount; }
}
