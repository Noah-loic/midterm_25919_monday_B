package auca.ac.rw.restfullApiAssignment.modal;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "waitlist")
public class Waitlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "waitlist_id")
    private Integer waitlistId;
    
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private StudentEntity student;
    
    @ManyToOne
    @JoinColumn(name = "course_code", nullable = false)
    private CourseEntity course;
    
    @Column(nullable = false)
    private Integer position;
    
    @Column(name = "waitlist_date")
    private LocalDateTime waitlistDate = LocalDateTime.now();
    
    private Boolean notified = false;

    public Integer getWaitlistId() { return waitlistId; }
    public void setWaitlistId(Integer waitlistId) { this.waitlistId = waitlistId; }
    
    public StudentEntity getStudent() { return student; }
    public void setStudent(StudentEntity student) { this.student = student; }
    
    public CourseEntity getCourse() { return course; }
    public void setCourse(CourseEntity course) { this.course = course; }
    
    public Integer getPosition() { return position; }
    public void setPosition(Integer position) { this.position = position; }
    
    public LocalDateTime getWaitlistDate() { return waitlistDate; }
    public void setWaitlistDate(LocalDateTime waitlistDate) { this.waitlistDate = waitlistDate; }
    
    public Boolean getNotified() { return notified; }
    public void setNotified(Boolean notified) { this.notified = notified; }
}
