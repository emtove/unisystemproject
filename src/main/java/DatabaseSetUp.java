import models.Course;
import models.User;
import services.CourseService;
import services.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;

/**
 * Created by Elev1 on 2017-04-24.
 */

@Singleton
public class databaseSetUp {

    @EJB
    UserService userService;
    @EJB
    CourseService courseService;

    @PostConstruct
    public void Init() {
        User user = new User();
        user.setFirstName("Olle");
        user.setLastName("Olsson");
        user.setEmail("email@email.se");
        user.setPassword("Olle1234");
        user.setStudent(true);
        userService.addUser(user);

        //user = new User();
        user.setFirstName("Magnus");
        user.setLastName("Larsson");
        user.setEmail("email@email.se");
        user.setPassword("Olle1234");
        user.setStudent(false);
        user.setTeacher(true);
        userService.addUser(user);

        //user.setStudentCourses();

        Course course = new Course();
        course.setName("Java");
        course.setDescription("En bra kurs");
        courseService.addCourse(course);
    }
}
