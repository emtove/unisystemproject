import models.User;
import services.UserService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
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
        //onChange1 = false;
        System.out.println("Setting roles!");
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
        System.out.println("getSelectedUserId: " + selectedUser);
        return selectedUser;
    }

    public void setSelectedUserId(long selectedUser) {
        System.out.println("setSelectedUserId: " + selectedUser);
        this.selectedUser = selectedUser;
    }

    public String getSelectedUserName() {
        return selectedUserName;
    }

    public void setSelectedUserName(String selectedUserName) {
        this.selectedUserName = selectedUserName;
    }

    public boolean isStudent() {
        //System.out.println("isStudent: " + student);
        return student;
    }

    public void setStudent(boolean student) {
        if (onChange1) {
            //this.student = u.isStudent();
            onChange1 = false;
        } else {
            this.student = student;
        }
    }

    public boolean isTeacher() {
        //System.out.println("isTeacher: " + teacher);
        return teacher;
    }

    public void setTeacher(boolean teacher) {
        if (onChange2) {
            //this.teacher = u.isTeacher();
            onChange2 = false;
        } else {
            this.teacher = teacher;
        }
    }
    public boolean isAdmin() {

        //System.out.println("isAdmin: " + admin);
        return admin;
    }

    public void setAdmin(boolean admin) {
        System.out.println("Setting Booleans");
        if (onChange3) {
            System.out.println("onChange == true");
            //this.admin = u.isAdmin();
            onChange3 = false;
        } else {
            System.out.println("Not here!!!");
            this.admin = admin;
        }
    }
}

