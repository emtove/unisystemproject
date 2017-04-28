package forms;

import login.AuthSessionManager;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class LogInForm {
    @Inject
    private AuthSessionManager authSessionManager;
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String submitForm() {
        FacesContext fc = FacesContext.getCurrentInstance();
        boolean success = authSessionManager.logIn(email, password);
        if (success) {
            return "home?faces-redirect=true;";
//            return fc.getViewRoot().getViewId() + "?faces-redirect=true;";
        } else {
            fc.addMessage("login-form", new FacesMessage("felaktigt användarnamn eller lösenord"));
            return "";
        }
    }
}

