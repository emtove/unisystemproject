package login.filters;

import models.User;

public class AdminAuthFilter extends AuthFilter {

    @Override
    boolean userRoleMatches(User user) {
        return user.isAdmin();
    }
}
