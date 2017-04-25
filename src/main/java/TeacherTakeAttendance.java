import models.AttendanceRecord;
import models.Course;
import models.User;
import services.AttendanceRecordService;
import services.CourseService;
import services.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.context.RequestScoped;
//import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.time.LocalDate;
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
    String[] attendanceById;

    @EJB
    UserService userService;
    @EJB
    CourseService courseService;
    @EJB
    AttendanceRecordService attendanceRecordService;



    @PostConstruct
    public void Init() {
//        User user = new User();
//        user.setFirstName("Olle");
//        user.setLastName("Olsson");
//        user.setEmail("email@email.se");
//        user.setPassword("Olle1234");
//        user.setStudent(true);
//        userService.addUser(user);
//
//        user = new User();
//        user.setFirstName("Magnus");
//        user.setLastName("Larsson");
//        user.setEmail("email@email.se");
//        user.setPassword("Olle1234");
//        user.setStudent(false);
//        user.setTeacher(true);
//        userService.addUser(user);
//        this.user = user;
//        //user.setStudentCourses();
//
//        Course course = new Course();
//        course.setName("Java");
//        course.setDescription("En bra kurs");
//        courseService.addCourse(course);
//
//        userService.addTeacherToCourse(user.getId(),course.getId());

        /*models.User user = authSessionManager.getUser();*/
        this.user = userService.getUser(user.getId());
        //Set<Course> set = user.getTeacherCourses();
        //for (Course c: set) {
            System.out.println("*****************************Magnus******************************");
//            System.out.println("***********************************************************");
//            //System.out.println(c.getName());
//            System.out.println("***********************************************************");

        //}

    }

    public void setAttenadnce(String[] attenadnce){
        attendanceById = attenadnce;
    }

    public String[] getAttenadnce(){
        String[] s = {""};
        return s;
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

    public void registerAttendance(){
        boolean present = false;
        LocalDate date = LocalDate.now();
        AttendanceRecord record;

        for (User u: students) {
            record = new AttendanceRecord();
            record.setUserId(u.getId());
            record.setCourseId(course.getId());
            record.setDate(date);
            for (String s: attendanceById) {
                if(u.getId()== Long.parseLong(s)){
                    present = true;
                    record.setPresent(true);
                }
            }
            if (!present){
                record.setPresent(false);
            }
            //Persist
            attendanceRecordService.addAttendanceRecord(record);
        }
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


