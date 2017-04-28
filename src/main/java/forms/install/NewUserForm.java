package forms.install;

import models.User;
import services.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@RequestScoped
public class NewUserForm {
    @EJB
    private UserService userService;
    private User user;

    @PostConstruct
    public void init() {
        user = new User();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void submitForm() {
        FacesContext fc = FacesContext.getCurrentInstance();
        boolean success = userService.addUser(user);
        if (success) {
            user = new User();
        } else {
            fc.addMessage("new-user-form", new FacesMessage("there is already an account with that email"));
        }
    }
}
