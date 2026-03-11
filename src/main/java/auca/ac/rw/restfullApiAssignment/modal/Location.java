package auca.ac.rw.restfullApiAssignment.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.*;

@Entity
@Table(name = "locations")
public class Location {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ELocationType type;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    // @com.fasterxml.jackson.annotation.JsonIgnoreProperties({"children", "students", "parent"})
    private Location parent;
    
    @JsonIgnore
    @OneToMany(mappedBy = "parent")
    private List<Location> children;
    
    @JsonIgnore
    @OneToMany(mappedBy = "location")
    private List<StudentEntity> students;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ELocationType getType() { return type; }
    public void setType(ELocationType type) { this.type = type; }

    public Location getParent() { return parent; }
    public void setParent(Location parent) { this.parent = parent; }
    
    public List<Location> getChildren() { return children; }
    public void setChildren(List<Location> children) { this.children = children; }
    
    public List<StudentEntity> getStudents() { return students; }
    public void setStudents(List<StudentEntity> students) { this.students = students; }
}
