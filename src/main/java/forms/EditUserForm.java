package forms;

import login.AuthSessionManager;
import models.Course;
import models.User;
import services.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class EditUserForm {
    @EJB
    private UserService userService;

    @Inject
    private AuthSessionManager authSessionManager;

    private long userId;
    private User user;

    @PostConstruct
    public void init() {
        user = authSessionManager.getUser();
    }

    public void updateUser() {
        userService.updateUser(user);
    }


    public long getUserId() {
        return userId;
    }

    public boolean isValidUser() {
        return user != null;
    }

    public void setUserId(long userId) {
        this.userId = userId;
        this.user = userService.getUser(userId);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
