package forms.install;

import models.Course;
import services.CourseService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class NewCourseForm {
    @EJB
    private CourseService courseService;
    private Course course;

    @PostConstruct
    public void init() {
        course = new Course();
    }

    public void submitForm() {
//        FacesContext fc = FacesContext.getCurrentInstance();
        boolean success = courseService.addCourse(course);
        course = new Course();
//        return fc.getViewRoot().getViewId() + "?faces-redirect=true;";
    }

    public List<Course> getCourses() {
        return courseService.getCourses();
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
