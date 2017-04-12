package services;

import models.Course;

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

    public boolean addCourse(Course course) {
        em.persist(course);
        return true;
    }

    // TODO: get specific course
    // TODO: remove course
    // TODO: update course
}
