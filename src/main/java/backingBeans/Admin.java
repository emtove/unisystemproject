package backingBeans;

import models.Course;
import models.User;
import services.CourseService;
import services.UserService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elev1 on 2017-04-25.
 */
@Named
@RequestScoped
public class Admin {

    private Course course;
    private String courseName;
    private String courseDescription;
    private long selectedCourse;
    private long selectedTeacher;

    @EJB
    CourseService courseService;
    @EJB
    UserService userService;

    public List<User> getAllTeachers(){
        List<User> users = userService.getUsers();
        List<User> teachers = new ArrayList<User>();
        for (User u: users) {
            if(u.isTeacher()){
                teachers.add(u);
            }
        }
        return teachers;
    }

    public List<Course> getCourses(){
        return courseService.getCourses();
    }

    public void addTeacherToCourse(){
        userService.addTeacherToCourse(selectedTeacher ,selectedCourse);
    }
    public void addCourse(){
        Course course = new Course();
        course.setName(courseName);
        course.setDescription(courseDescription);
        courseService.addCourse(course);
    }

    public String getCourseName() {
        return "";
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return "";
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public long getSelectedCourse() {
        return selectedCourse;
    }

    public void setSelectedCourse(long selectedCourse) {
        this.selectedCourse = selectedCourse;
    }

    public long getSelectedTeacher() {
        return selectedTeacher;
    }

    public void setSelectedTeacher(long selectedTeacher) {
        this.selectedTeacher = selectedTeacher;
    }
}
