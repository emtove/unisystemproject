package login.filters;

import login.AuthSessionManager;
import models.User;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class intercepts requests for pages (specified in web.xml) that need specific
 * permissions to view. If the user doesn't have the required permission (eg. the user
 * tries to view the admin panel and isn't an admin) the user is redirected to the
 * login page.
 *
 * Note that this is an abstract class that provides the basis for the three different
 * roles a user can have. Its subclasses are what are used in web.xml.
 */
public abstract class AuthFilter implements Filter {
    @Inject
    private AuthSessionManager authSessionManager;
    private FilterConfig filterConfig;

    abstract boolean userRoleMatches(User user);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        if (authSessionManager.isLoggedIn() && userRoleMatches(authSessionManager.getUser())) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            // TODO: change this if needed, it's just a placeholder
            String loginPageUrl = "/login.xhtml";
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            ((HttpServletResponse) servletResponse).sendRedirect(
                    servletRequest.getServletContext().getContextPath() + loginPageUrl);
        }
    }

    @Override
    public void destroy() {

    }
}
