package services;


import models.User;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.sql.SQLException;
import java.util.List;

@Local
@Stateless
public class UserService {
    @PersistenceContext
    private EntityManager em;

    public List<User> getUsers() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

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

    // TODO: modify student/teacher/admin privileges
}
