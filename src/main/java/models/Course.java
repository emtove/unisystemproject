package models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
public class Course implements Serializable {
    @Id @GeneratedValue
    private long id;
    @NotNull
    private String name;
    private String description;
    @ManyToMany
    @JoinTable(
            name = "CourseStudents",
            joinColumns = @JoinColumn(name = "Course", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "Users", referencedColumnName = "id")
    )
    private List<User> students;
    @ManyToMany
    @JoinTable(
            name = "CourseTeachers",
            joinColumns = @JoinColumn(name = "Course", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "Users", referencedColumnName = "id")
    )
    private List<User> teachers;

    public Course() {}

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getStudents() {
        return students;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }
}
