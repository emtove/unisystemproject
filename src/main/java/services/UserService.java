package services;


import models.Course;
import models.User;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.sql.SQLException;
import java.util.List;

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
     * Updates the specified user with the new data, updating all connected courses
     * in the process.
     */
    public void updateUser(User newUser) {
        User oldUser = getUser(newUser.getId());
        // This is here to update the other end of the many-to-many connection.
        UserCourseCommon.updateUsersOrCourses(oldUser, newUser, Course::getStudents, User::getStudentCourses, Course.class, em);
        UserCourseCommon.updateUsersOrCourses(oldUser, newUser, Course::getTeachers, User::getTeacherCourses, Course.class, em);
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
