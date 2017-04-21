import models.Course;
import models.User;
import services.CourseService;
import services.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.context.RequestScoped;
//import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

/**
 * Created by Elev1 on 2017-04-12.
 */
@Named
@SessionScoped
public class TeacherTakeAttendance implements Serializable{

    private User user;
    //private long selectedCourse;
    //private String selectedCourse = "Default";
    Course course;
    ArrayList<User> students;

    @EJB
    UserService userService;
    @EJB
    CourseService courseService;

    @PostConstruct
    public void Init() {
         /*models.User user = authSessionManager.getUser();*/
        user = userService.getUser(3);
        //Set<Course> set = user.getTeacherCourses();
        //for (Course c: set) {
            System.out.println("*****************************Magnus******************************");
//            System.out.println("***********************************************************");
//            //System.out.println(c.getName());
//            System.out.println("***********************************************************");

        //}

    }

    public List<Course> getMyCourses(){
        //return new ArrayList<Course>();
        ArrayList<Course> courses =  new ArrayList<Course>(user.getTeacherCourses());
        courses.sort(new CourseComparator());
        return courses;
    }

    public String takeAttendanceForCourse(long courseId){
        course = courseService.getCourse(courseId);
        System.out.println("Getting students for Course: " + course.getName() + " with Id: " + course.getId() + " after finding course with Id: " + courseId);

        Set<User> s = course.getStudents();
        for (User u : s) {
            System.out.println("Student(Set): " + u.getFirstName());
        }

        students = new ArrayList<User>(course.getStudents());

        for (User u : students) {
            System.out.println("Student(ArrayList): " + u.getFirstName());
        }
        return "teacherTakeAttendance";
    }

    public ArrayList<User> getStudents(){
        if (students != null) {
            for (User u : students) {
                System.out.println("Student(ArrayList part2): " + u.getFirstName());
            }
        }
        return students;
    }

    public Course getCourse(){
        return course;
    }

//    public String getSelectedCourse(){
//
//        return selectedCourse;
//    }
//
//    public void changeSelectedCourse(ValueChangeEvent e){
//        selectedCourse = "hej";
//        System.out.println("*************************Event**********************************");
//        //selectedCourse = e.getNewValue().toString();
//    }
}


