import login.AuthSessionManager;
import models.Course;
import services.CourseService;
import services.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Elev1 on 2017-04-11.
 */
@RequestScoped
@Named
public class Student {

    private models.User user;
    private String selectedCourse;


    @EJB
    CourseService courseService;
    /*EJB
    AuthSessionManager authSessionManager;
    */
    @EJB
    UserService userService;

    @PostConstruct
    public void Init() {
         /*models.User user = authSessionManager.getUser();*/
        user = userService.getUser(1);

    }

    public List<Course> getRegistredCourses(){
//        Set<Course> set = user.getStudentCourses();
//        for (Course c: set) {
//            System.out.println(c.getName());
//        }

        return new ArrayList<Course>(user.getStudentCourses());
    }

    public List<Course> getAllCourses(){
        return courseService.getCourses();
    }

    public void applyForCourse(){

    }

    public void deRegisterForCourse(long id){
        System.out.println("UserID " + user.getId() + " kursId " + id);
        userService.removeStudentFromCourse(user.getId(), id);
    }

    public void getAttendenceForCourse(long id){

    }

    public ArrayList<AttendanceDate> getAttendenceArray() {
        return new ArrayList<AttendanceDate>();
    }

    public String getFirstName() {
        return user.getFirstName();
    }

    public String getLastName() {
        return user.getLastName();
    }

}

