package login.filters;

public class LoggedInFilter extends AuthFilter {

    String handleRequest() {
        if (authSessionManager.isLoggedIn()) {
            return "/home.xhtml";
        } else {
            return null;
        }
    }
}
