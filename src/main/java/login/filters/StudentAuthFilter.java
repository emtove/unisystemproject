package login.filters;

import models.User;

public class StudentAuthFilter extends AuthFilter {

    @Override
    boolean userRoleMatches(User user) {
        return user.isStudent();
    }
}
