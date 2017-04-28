package forms;

import login.AuthSessionManager;
import models.User;
import services.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.POST;
import java.util.Locale;
import java.util.ResourceBundle;

@Named
@RequestScoped
public class RegisterForm {
    @EJB
    private UserService userService;
    private User user;
    private String repeatedPassword;

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

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    public String submitForm() {
        FacesContext fc = FacesContext.getCurrentInstance();
        if (repeatedPassword.equals(user.getPassword())) {
            boolean success = userService.addUser(user);
            if (success) {
                return "register-success";
            } else {
                fc.addMessage("register-form", new FacesMessage("there is already an account with that email"));
                return "";
            }
        } else {
            Locale locale = FacesContext.getCurrentInstance().getApplication().getDefaultLocale();
            fc.addMessage("register-form", new FacesMessage("passwords do not match"));
            return "";
        }
    }
}

