package services;

import models.Course;
import models.User;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Local
@Stateless
public class CourseService {
    @PersistenceContext
    private EntityManager em;

    public List<Course> getCourses() {
        return em.createQuery("select c from Course c", Course.class).getResultList();
    }

    public Course getCourse(long id) {
        return em.find(Course.class, id);
    }

    public boolean addCourse(Course course) {
        em.persist(course);
        return true;
    }

    public void removeCourse(long id) {
        Course course = getCourse(id);
        if (course != null) {
            em.remove(course);
        }
    }

    public void updateCourse(Course newCourse) {
        Course oldCourse = getCourse(newCourse.getId());
        // This is here to update the other end of the many-to-many connection.
        UserCourseCommon.updateUsersOrCourses(oldCourse, newCourse, User::getStudentCourses, Course::getStudents, User.class, em);
        UserCourseCommon.updateUsersOrCourses(oldCourse, newCourse, User::getTeacherCourses, Course::getTeachers, User.class, em);
        em.merge(newCourse);
    }
}
