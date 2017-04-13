package services;


import models.Course;
import models.User;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * The UserService class is in charge of adding/removing/modifying the users
 * in the database.
 */

@Local
@Stateless
public class UserService {
    @PersistenceContext
    private EntityManager em;

    public List<User> getUsers() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    public User getUser(long id) {
        return em.find(User.class, id);
    }

    /**
     * Adds a new user to the database unless any of the user's unique attributes
     * conflicts with an existing user in the database.
     * @param user
     * @return
     */
    public boolean addUser(User user) {
        try {
            em.persist(user);
            em.flush();
            return true;
        } catch (PersistenceException pe) {
            // TODO: no idea if this is okay in the long run
            if (pe.getCause().getCause() instanceof SQLException) {
                SQLException s = (SQLException)pe.getCause().getCause();
                // 23505 is a postgresql-specific error code for unique constraints
                if (s.getSQLState().equals("23505")) {
                    return false;
                }
            }
            throw pe;
        }
    }

    public void removeUser(long id) {
        User user = getUser(id);
        if (user != null) {
            em.remove(user);
        }
    }

    /**
     * This is a helper function for updateUser to cut down on the needless
     * boilerplate code when updating all relevant courses with added/removed users.
     *  @param user
     * @param getUserList
     * @param oldCourses
     * @param newCourses
     */
    private void updateCoursesForUser(User user, Function<Course, Set<User>> getUserList,
                                      Set<Course> oldCourses, Set<Course> newCourses) {
        // Add the user to all added courses
        Set<Course> coursesToAdd = new HashSet<>(newCourses);
        coursesToAdd.removeAll(oldCourses);
        for (Course c : coursesToAdd) {
            getUserList.apply(em.find(Course.class, c.getId())).add(user);
        }
        // Remove the user from all removed courses
        Set<Course> coursesToRemove = new HashSet<>(oldCourses);
        coursesToRemove.removeAll(newCourses);
        for (Course c : coursesToRemove) {
            getUserList.apply(em.find(Course.class, c.getId())).remove(user);
        }
    }

    /**
     * Updates the specified user with the new data, updating all connected courses
     * in the process.
     *
     * @param newUser
     */
    public void updateUser(User newUser) {
        User oldUser = getUser(newUser.getId());
        // TODO: cut down more on the ugly boilerplate here
        // update the other side if the course sets aren't the same
        if (!oldUser.getStudentCourses().equals(newUser.getStudentCourses())) {
            updateCoursesForUser(oldUser, Course::getStudents, oldUser.getStudentCourses(), newUser.getStudentCourses()
            );
        }
        if (!oldUser.getTeacherCourses().equals(newUser.getTeacherCourses())) {
            updateCoursesForUser(oldUser, Course::getTeachers, oldUser.getTeacherCourses(), newUser.getTeacherCourses()
            );
        }
        em.merge(newUser);
    }

    /**
     * Checks a potential user's credentials against the database and returns the user
     * with the specified credentials if they are valid.
     *
     * @param email
     * @param password
     * @return          the matching user or null if none is found
     */
    public User validateUserCredentials(String email, String password) {
        // TODO: password should not be in plaintext
        TypedQuery<User> query = em.createQuery("select u from User u where u.email = :email and u.password = :password", User.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        try {
            return query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public boolean addStudentToCourse(long userId, long courseId) {
        User user = em.find(User.class, userId);
        if (user.isStudent()) {
            Course course = em.find(Course.class, courseId);
            user.getStudentCourses().add(course);
            course.getStudents().add(user);
            return true;
        } else {
            return false;
        }
    }

    public void removeStudentFromCourse(long userId, long courseId) {
        User user = em.find(User.class, userId);
        Course course = em.find(Course.class, courseId);
        user.getStudentCourses().remove(course);
        course.getStudents().remove(user);
    }

    public boolean addTeacherToCourse(long userId, long courseId) {
        User user = em.find(User.class, userId);
        if (user.isTeacher()) {
            Course course = em.find(Course.class, courseId);
            user.getTeacherCourses().add(course);
            course.getTeachers().add(user);
            return true;
        } else {
            return false;
        }
    }

    public void removeTeacherFromCourse(long userId, long courseId) {
        User user = em.find(User.class, userId);
        Course course = em.find(Course.class, courseId);
        user.getTeacherCourses().remove(course);
        course.getTeachers().remove(user);
    }
}
