package auca.ac.rw.restfullApiAssignment.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "students")
public class StudentEntity {
    @Id
    @Column(name = "student_id", length = 20)
    private String studentId;
    
    @Column(nullable = false)
    private String name;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    private String phone;
    
    @Column(columnDefinition = "TEXT")
    private String address;
    
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
    
    @JsonIgnore
    @OneToOne(mappedBy = "student")
    private User user;
    
    @JsonIgnore
    @OneToMany(mappedBy = "student")
    private List<Registration> registrations;
    
    @JsonIgnore
    @OneToMany(mappedBy = "student")
    private List<Payment> payments;
    
    @JsonIgnore
    @OneToMany(mappedBy = "student")
    private List<Waitlist> waitlists;
    
    @Column(name = "max_credits")
    private Integer maxCredits = 18;
    
    @Column(name = "min_credits")
    private Integer minCredits = 12;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public List<Registration> getRegistrations() { return registrations; }
    public void setRegistrations(List<Registration> registrations) { this.registrations = registrations; }
    
    public List<Payment> getPayments() { return payments; }
    public void setPayments(List<Payment> payments) { this.payments = payments; }
    
    public List<Waitlist> getWaitlists() { return waitlists; }
    public void setWaitlists(List<Waitlist> waitlists) { this.waitlists = waitlists; }
    
    public Integer getMaxCredits() { return maxCredits; }
    public void setMaxCredits(Integer maxCredits) { this.maxCredits = maxCredits; }
    
    public Integer getMinCredits() { return minCredits; }
    public void setMinCredits(Integer minCredits) { this.minCredits = minCredits; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
