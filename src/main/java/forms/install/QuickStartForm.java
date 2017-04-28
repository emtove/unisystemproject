package forms.install;

import models.Course;
import models.User;
import services.CourseService;
import services.SettingsService;
import services.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class QuickStartForm {
    @EJB
    private CourseService courseService;
    @EJB
    private UserService userService;
    @EJB
    private SettingsService settingsService;

    @PostConstruct
    public void init() {
    }

    public void fillDatabase() {
        String[] courseNames = {"Java", "XML", "ASP.NET", "Ada", "FORTRAN", "C++", "Paint"};
        List<Course> courses = new ArrayList<>();
        for (String courseName : courseNames) {
            Course course = new Course();
            course.setName(courseName);
            course.setDescription("En kurs om " + courseName);
            courseService.addCourse(course);
            courses.add(course);
        }
        String[] firstNames = {"Alfa", "Beta", "Gamma", "Delta", "Epsilon", "Zeta", "Eta", "Theta"};
        for (int i = 0; i < firstNames.length; i++) {
            User user = new User();
            user.setFirstName(firstNames[i]);
            user.setLastName("Testsson");
            user.setEmail(firstNames[i].toLowerCase() + "@test.com");
            user.setPassword("12345678");
            if (i == 0 || i == 1) {
                user.setAdmin(true);
            }
            if (i == 1 || i == 2 || i == 3) {
                user.setTeacher(true);
            }
            if (i >= 3) {
                user.setStudent(true);
            }
            userService.addUser(user);
            if (i == 2) {
                userService.addTeacherToCourse(user.getId(), courses.get(0).getId());
                userService.addTeacherToCourse(user.getId(), courses.get(1).getId());
            }
            if (i == 3) {
                userService.addTeacherToCourse(user.getId(), courses.get(1).getId());
                userService.addTeacherToCourse(user.getId(), courses.get(3).getId());
            }
            if (i >= 3) {
                userService.addStudentToCourse(user.getId(), courses.get(0).getId());
                userService.addStudentToCourse(user.getId(), courses.get(3).getId());
            }
            if (i > 5) {
                userService.addStudentToCourse(user.getId(), courses.get(1).getId());
                userService.addStudentToCourse(user.getId(), courses.get(5).getId());
            }
        }
    }

    public String finishInstall() {
        settingsService.setSetting("installed", "true");
        return "/index?faces-redirect=true;";
    }
}
