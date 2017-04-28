package backingBeans;

import models.User;
import services.UserService;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Elev1 on 2017-04-26.
 */
@Named
@SessionScoped
public class AdminSetRoles implements Serializable {

    private User u;
    private long selectedUser = 0;
    private String selectedUserName = "";
    private boolean onChange1;
    private boolean onChange2;
    private boolean onChange3;
    private boolean student;
    private boolean teacher;
    private boolean admin;

    @EJB
    UserService userService;

    public void setRoles() {
            User u = userService.getUser(selectedUser);
            u.setStudent(student);
            u.setTeacher(teacher);
            u.setAdmin(admin);
            System.out.println(u.getFirstName() + " " + u.getId() + " Student: " + u.isStudent() + " Teacher: " + u.isTeacher() + " Admin: " + u.isAdmin());
            userService.updateUser(u);
            u = userService.getUser(u.getId());
            System.out.println(u.getFirstName() + " " + u.getId() + " Student: " + u.isStudent() + " Teacher: " + u.isTeacher() + " Admin: " + u.isAdmin());
    }

    public String selectUser(ValueChangeEvent e) {
            onChange1 = true;
            onChange2 = true;
            onChange3 = true;

            u = userService.getUser(Long.parseLong(e.getNewValue().toString()));

            selectedUserName = u.getFirstName() + " " + u.getLastName();
            System.out.println("Eventvalue: " + e.getNewValue().toString() + selectedUserName + " " + u.isStudent() + " " + u.isTeacher() + " " + u.isAdmin());
            student = u.isStudent();
            teacher = u.isTeacher();
            admin = u.isAdmin();
            return "adminSetRoles?faces-redirect=true;";
    }

    public List<User> getAllUsers() {
        return userService.getUsers();
    }

    public long getSelectedUserId() {
        return selectedUser;
    }

    public void setSelectedUserId(long selectedUser) {
        this.selectedUser = selectedUser;
    }

    public String getSelectedUserName() {
        return selectedUserName;
    }

    public void setSelectedUserName(String selectedUserName) {
        this.selectedUserName = selectedUserName;
    }

    public boolean isStudent() {
        return student;
    }

    public void setStudent(boolean student) {
        if (onChange1) {
            onChange1 = false;
        } else {
            this.student = student;
        }
    }

    public boolean isTeacher() {
        return teacher;
    }

    public void setTeacher(boolean teacher) {
        if (onChange2) {
            onChange2 = false;
        } else {
            this.teacher = teacher;
        }
    }
    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        if (onChange3) {
            onChange3 = false;
        } else {
            this.admin = admin;
        }
    }
}

