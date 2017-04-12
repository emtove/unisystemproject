package login.filters;

import login.AuthSessionManager;
import models.User;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            ((HttpServletResponse) servletResponse).sendRedirect(servletRequest.getServletContext().getContextPath() + "/nope.xhtml");
        }
    }

    @Override
    public void destroy() {

    }
}
