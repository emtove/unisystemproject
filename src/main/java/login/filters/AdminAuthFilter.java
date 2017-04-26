package login.filters;

public class AdminAuthFilter extends AuthFilter {

    @Override
    String handleRequest() {
        if (authSessionManager.isLoggedIn() && authSessionManager.getUser().isAdmin()) {
            return null;
        } else {
            return "/index.xhtml";
        }
    }
}
