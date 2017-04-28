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
    private long selectedCourseAttendnce;
    private long selectedCourse;

    @EJB
    CourseService courseService;
    @EJB
    UserService userService;
    @EJB
    AttendanceRecordService attendanceRecordService;
    @EJB
    AuthSessionManager authSessionManager;

    @PostConstruct
    public void Init() {
        models.User user = authSessionManager.getUser();
    }

    public List<AttendanceRecord> getAttendanceRecord(){
        return attendanceRecordService.getAttendanceByCourseAndUser(selectedCourseAttendnce,user.getId());
    }

    public void showAttendenceForCourse(long courseId){
        selectedCourseAttendnce = courseId;
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
        return attendanceRecordService.getAttendanceByCourseAndUser(selectedCourseAttendnce, user.getId());
    }

    public String getCourseName(){
        return courseService.getCourse(selectedCourseAttendnce).getName();
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

