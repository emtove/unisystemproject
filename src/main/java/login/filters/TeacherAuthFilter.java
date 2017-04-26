package login.filters;

public class TeacherAuthFilter extends AuthFilter {

    @Override
    String handleRequest() {
        if (authSessionManager.isLoggedIn() && authSessionManager.getUser().isTeacher()) {
            return null;
        } else {
            return "/index.xhtml";
        }
    }
}
