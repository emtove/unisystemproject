package login.filters;

public class StudentAuthFilter extends AuthFilter {

    @Override
    String handleRequest() {
        if (authSessionManager.isLoggedIn() && authSessionManager.getUser().isStudent()) {
            return null;
        } else {
            return "/index.xhtml";
        }
    }
}
