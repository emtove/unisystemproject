package backingBeans;

import login.AuthSessionManager;
import models.AttendanceRecord;
import models.Course;
import services.AttendanceRecordService;
import services.CourseService;
import services.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elev1 on 2017-04-11.
 */
@RequestScoped
@Named
public class Student {

    private models.User user;
    private long selectedCourseAttendance;
    private long selectedCourse;

    @EJB
    private CourseService courseService;
    @EJB
    private UserService userService;
    @EJB
    private AttendanceRecordService attendanceRecordService;
    @Inject
    private AuthSessionManager authSessionManager;

    @PostConstruct
    public void init() {
        user = authSessionManager.getUser();
    }

    public List<AttendanceRecord> getAttendanceRecord(){
        return attendanceRecordService.getAttendanceByCourseAndUser(selectedCourseAttendance,user.getId());
    }

    public void showAttendenceForCourse(long courseId){
        selectedCourseAttendance = courseId;
    }

    public List<Course> getRegisteredCourses() {
        List<Course> courses = new ArrayList<>(user.getStudentCourses());
        courses.sort(new CourseComparator());
        return courses;
    }

    public List<Course> getAllCourses(){
        return courseService.getCourses();
    }

    public String applyForCourse(){
        userService.addStudentToCourse(user.getId(),selectedCourse);
        return "student?faces-redirect=true;";
    }

    public String deRegisterForCourse(long id){
        userService.removeStudentFromCourse(user.getId(), id);
        return "student?faces-redirect=true;";
    }

    public List<AttendanceRecord> getAttendenceArray() {
        return attendanceRecordService.getAttendanceByCourseAndUser(selectedCourseAttendance, user.getId());
    }

    public String getCourseName(){
        return courseService.getCourse(selectedCourseAttendance).getName();
    }

    public String getFirstName() {
        return user.getFirstName();
    }

    public String getLastName() {
        return user.getLastName();
    }

    public long getSelectedCourse() {
        return selectedCourse;
    }

    public void setSelectedCourse(long selectedCourse) {
        this.selectedCourse = selectedCourse;
    }
}

