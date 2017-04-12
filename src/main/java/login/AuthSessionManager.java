package login;

import models.User;
import services.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;

/**
 * The AuthSessionManager keeps track of the currently logged in user (if any)
 * and their permissions.
 */

@Named
@SessionScoped
public class AuthSessionManager implements Serializable {
    @EJB
    private UserService userService;
    private boolean loggedIn;
    private User user;

    @PostConstruct
    public void init() {
        loggedIn = false;
        user = null;
    }

    public boolean logIn(String email, String password) {
        User validatedUser = userService.validateUserCredentials(email, password);
        if (validatedUser != null) {
            loggedIn = true;
            user = validatedUser;
            return true;
        } else {
            return false;
        }
    }

    public String logOut() {
        if (loggedIn) {
            user = null;
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            return "/index?faces-redirect=true;";
        } else {
            return "";
        }
    }
    public boolean isLoggedIn() {
        return loggedIn;
    }

    public User getUser() {
        return user;
    }
}
