package login.filters;

import models.User;

public class TeacherAuthFilter extends AuthFilter {

    @Override
    boolean userRoleMatches(User user) {
        return user.isTeacher();
    }
}
