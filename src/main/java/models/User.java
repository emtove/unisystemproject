package models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
// this table has to be called "users" since "user" is a reserved name in sql
@Table(name = "Users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class User implements Serializable {
    @Id @GeneratedValue
    private long id;
    @NotNull
    private String email;
    @NotNull @Size(min = 6)
    private String password;
    private String firstName;
    private String lastName;

    @ElementCollection(targetClass = UserRole.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "UserRoles", joinColumns = {@JoinColumn(name = "userId")})
    private Set<UserRole> roles;

    @ManyToMany(mappedBy = "students")
    private Set<Course> studentCourses;
    @ManyToMany(mappedBy = "teachers")
    private Set<Course> teacherCourses;

    public User() {
        roles = new HashSet<>();
    }

    public String toString() {
        return id + ":" + email;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private void modifyRole(UserRole role, boolean present) {
        if (present) {
            roles.add(role);
        } else {
            roles.remove(role);
        }
    }

    public boolean isStudent() {
        return roles.contains(UserRole.STUDENT);
    }

    public void setStudent(boolean student) {
        modifyRole(UserRole.STUDENT, student);
    }

    public boolean isTeacher() {
        return roles.contains(UserRole.TEACHER);
    }

    public void setTeacher(boolean teacher) {
        modifyRole(UserRole.TEACHER, teacher);
    }

    public boolean isAdmin() {
        return roles.contains(UserRole.ADMIN);
    }

    public void setAdmin(boolean admin) {
        modifyRole(UserRole.ADMIN, admin);
    }

    public Set<Course> getStudentCourses() {
        return studentCourses;
    }

    public void setStudentCourses(Set<Course> studentCourses) {
        this.studentCourses = studentCourses;
    }

    public Set<Course> getTeacherCourses() {
        return teacherCourses;
    }

    public void setTeacherCourses(Set<Course> teacherCourses) {
        this.teacherCourses = teacherCourses;
    }
}
