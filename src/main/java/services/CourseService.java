package services;

import models.Course;
import models.User;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

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

    private void updateUsersForCourse(Course course, Function<User, Set<Course>> getCourseList,
                                      Set<User> oldUsers, Set<User> newUsers) {
        // Add the course to all added users
        Set<User> usersToAdd = new HashSet<>(newUsers);
        usersToAdd.removeAll(oldUsers);
        for (User u : usersToAdd) {
            getCourseList.apply(em.find(User.class, u.getId())).add(course);
        }
        // Remove the course from all removed users
        Set<User> usersToRemove = new HashSet<>(oldUsers);
        usersToRemove.removeAll(newUsers);
        for (User u : usersToRemove) {
            getCourseList.apply(em.find(User.class, u.getId())).remove(course);
        }
    }

    public void updateCourse(Course newCourse) {
        //TODO: less boilerplate here too, possibly try to combine it with UserService's counterpart?
        Course oldCourse = getCourse(newCourse.getId());
        if (!oldCourse.getStudents().equals(newCourse.getStudents())) {
            updateUsersForCourse(oldCourse, User::getStudentCourses, oldCourse.getStudents(), newCourse.getStudents());
        }
        if (!oldCourse.getTeachers().equals(newCourse.getTeachers())) {
            updateUsersForCourse(oldCourse, User::getTeacherCourses, oldCourse.getTeachers(), newCourse.getTeachers());
        }
        em.merge(newCourse);
    }
}
