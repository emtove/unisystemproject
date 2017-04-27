
import login.AuthSessionManager;
import models.AttendanceRecord;
import models.Course;
import models.User;
import services.AttendanceRecordService;
import services.CourseService;
import services.UserService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Created by Elev1 on 2017-04-25.
 */
@RequestScoped
@Named
public class MakeDatabase {

    private String email;
    private String passWord;

    @Inject
    AttendanceRecordService attendanceRecordService;


    @Inject
    AuthSessionManager authSessionManager;

    //Debug
    @EJB
    UserService userService;
    @EJB
    CourseService courseService;
    public String getEmail() {
        return "";
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassWord() {
        return "";
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String LogIn(){
        if (authSessionManager.logIn(email,passWord)){
            //if ()
            return "";
        }
        else {
            return "index";
        }
    }

    public void BuildMyDB(){

        User user = new User();
        user.setFirstName("Olle");
        user.setLastName("Olsson");
        user.setEmail("email@email.se");
        user.setPassword("Olle1234");
        user.setStudent(true);
        userService.addUser(user);

        //Lärare
        user = new User();
        user.setFirstName("Magnus");
        user.setLastName("Larsson");
        user.setEmail("email2@email.se");
        user.setPassword("Olle1234");
        user.setStudent(false);
        user.setTeacher(true);
        userService.addUser(user);

        user = new User();
        user.setFirstName("Heidar");
        user.setLastName("Larsson");
        user.setEmail("email3@email.se");
        user.setPassword("Olle1234");
        user.setStudent(true);
        userService.addUser(user);

        user = new User();
        user.setFirstName("Emelie");
        user.setLastName("Larsson");
        user.setEmail("email4@email.se");
        user.setPassword("Olle1234");
        user.setStudent(true);
        userService.addUser(user);


        Course course = new Course();
        course.setName("Java");
        course.setDescription("En bra kurs");
        courseService.addCourse(course);

        course = new Course();
        course.setName("XML");
        course.setDescription("Tråkig som fan");
        courseService.addCourse(course);

        course = new Course();
        course.setName("Heidars HTML");
        course.setDescription("En bra kurs");

        courseService.addCourse(course);course = new Course();
        course.setName("Javascript");
        course.setDescription("Som java fast script");
        courseService.addCourse(course);

        List<User> userList = userService.getUsers();

        List<Course> courseList = courseService.getCourses();

        //Set<Course> courseSet = new HashSet<Course>(courseList); //???
        userService.addStudentToCourse(userList.get(0).getId(),courseList.get(0).getId());
        userService.addStudentToCourse(userList.get(0).getId(),courseList.get(1).getId());
        userService.addStudentToCourse(userList.get(0).getId(),courseList.get(2).getId());
        userService.addStudentToCourse(userList.get(2).getId(),courseList.get(0).getId());
        userService.addStudentToCourse(userList.get(2).getId(),courseList.get(1).getId());
        userService.addStudentToCourse(userList.get(2).getId(),courseList.get(2).getId());
        userService.addStudentToCourse(userList.get(3).getId(),courseList.get(0).getId());
        userService.addStudentToCourse(userList.get(3).getId(),courseList.get(1).getId());
        userService.addStudentToCourse(userList.get(3).getId(),courseList.get(2).getId());


        userService.addTeacherToCourse(userService.getUser(2).getId(),courseList.get(0).getId());
        userService.addTeacherToCourse(userService.getUser(2).getId(),courseList.get(1).getId());
        userService.addTeacherToCourse(userService.getUser(2).getId(),courseList.get(2).getId());

        AttendanceRecord a = new AttendanceRecord();
        a.setDate(LocalDate.now());
        a.setPresent(true);
        a.setCourseId(5);
        a.setUserId(1);
        attendanceRecordService.addAttendanceRecord(a);

        a = new AttendanceRecord();
        a.setDate(LocalDate.now());
        a.setPresent(true);
        a.setCourseId(6);
        a.setUserId(1);
        attendanceRecordService.addAttendanceRecord(a);

        a = new AttendanceRecord();
        a.setDate(LocalDate.now());
        a.setPresent(true);
        a.setCourseId(7);
        a.setUserId(1);
        attendanceRecordService.addAttendanceRecord(a);

        a = new AttendanceRecord();
        a.setDate(LocalDate.now().minus(1, ChronoUnit.DAYS));
        a.setPresent(true);
        a.setCourseId(5);
        a.setUserId(1);
        attendanceRecordService.addAttendanceRecord(a);

        a = new AttendanceRecord();
        a.setDate(LocalDate.now().minus(1, ChronoUnit.DAYS));
        a.setPresent(true);
        a.setCourseId(6);
        a.setUserId(1);
        attendanceRecordService.addAttendanceRecord(a);

        a = new AttendanceRecord();
        a.setDate(LocalDate.now().minus(1, ChronoUnit.DAYS));
        a.setPresent(false);
        a.setCourseId(7);
        a.setUserId(1);
        attendanceRecordService.addAttendanceRecord(a);

    }

}
