package models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Course implements Identifiable, Serializable {
    @Id @GeneratedValue
    private long id;
    @NotNull(message = "name can not be empty")
    private String name;
    private String description;
    @ManyToMany
    @JoinTable(
            name = "CourseStudents",
            joinColumns = @JoinColumn(name = "courseId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "userId", referencedColumnName = "id")
    )
    private Set<User> students;
    @ManyToMany
    @JoinTable(
            name = "CourseTeachers",
            joinColumns = @JoinColumn(name = "courseId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "userId", referencedColumnName = "id")
    )
    private Set<User> teachers;

    public Course() {
    }

    public String toString() {
        return id + ":'" + name + "'";
    }

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

    public Set<User> getStudents() {
        return students;
    }

    public void setStudents(Set<User> students) {
        this.students = students;
    }

    public Set<User> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<User> teachers) {
        this.teachers = teachers;
    }
}
