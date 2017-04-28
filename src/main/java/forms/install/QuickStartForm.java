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
        String[] firstNames = {"Alfa", "Beta", "Gamma", "Delta", "Epsilon", "Zeta", "Eta", "Theta"};
        for (String firstName : firstNames) {
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName("Testsson");
            user.setEmail(firstName.toLowerCase() + "@test.com");
            user.setPassword("12345678");
            userService.addUser(user);
        }
        String[] courseNames = {"Java", "XML", "ASP.NET", "Ada", "FORTRAN", "C++", "Paint"};
        for (String courseName : courseNames) {
            Course course = new Course();
            course.setName(courseName);
            course.setDescription("En kurs om " + courseName);
            courseService.addCourse(course);
        }
    }

    public String finishInstall() {
        settingsService.setSetting("installed", "true");
        return "/index?faces-redirect=true;";
    }
}
