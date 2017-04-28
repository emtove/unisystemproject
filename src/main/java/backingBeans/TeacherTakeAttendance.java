package backingBeans;

import login.AuthSessionManager;
import models.AttendanceRecord;
import models.Course;
import models.User;
import services.AttendanceRecordService;
import services.CourseService;
import services.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//import javax.faces.bean.SessionScoped;

/**
 * Created by Elev1 on 2017-04-12.
 */
@Named
@SessionScoped
public class TeacherTakeAttendance implements Serializable{

    private User user;
    Course course;
    ArrayList<User> students;
    String[] attendanceById;

    @EJB
    UserService userService;
    @EJB
    CourseService courseService;
    @EJB
    AttendanceRecordService attendanceRecordService;
    @EJB
    AuthSessionManager authSessionManager;


    @PostConstruct
    public void Init() {
    User user = authSessionManager.getUser();
    }

    public void setAttenadnce(String[] attenadnce){
        attendanceById = attenadnce;
    }

    public String[] getAttenadnce(){
        String[] s = {""};
        return s;
    }

    public List<Course> getMyCourses(){
        ArrayList<Course> courses =  new ArrayList<Course>(user.getTeacherCourses());
        courses.sort(new CourseComparator());
        return courses;
    }

    public String takeAttendanceForCourse(long courseId){
        course = courseService.getCourse(courseId);

        Set<User> s = course.getStudents();
        students = new ArrayList<User>(course.getStudents());
        students.sort(new StudentComparator());
        return "teacherTakeAttendance";
    }

    public ArrayList<User> getStudents(){
        if (students != null) {
            students.sort(new StudentComparator());
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
            attendanceRecordService.updateAttendanceRecord(record);
        }
    }
}


