package by.sadko.training.filter;

import by.sadko.training.SecurityContext;
import by.sadko.training.command.AppCommandName;
import by.sadko.training.command.CommandUtil;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.sadko.training.util.AppConstants.PARAM_COMMAND;

/**
 * Security filter
 *
 * @author Sadko Kiryl
 * @version 1.0
 */
@WebFilter(servletNames = "index", filterName = "security_filter")
public class CommandSecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * Checking permission of a current user
     *
     * @param servletRequest  - request
     * @param servletResponse - response
     * @param filterChain     - filter chain
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        SecurityContext securityContext = SecurityContext.getInstance();

        String sessionId = request.getSession().getId();
        securityContext.setCurrentSessionId(sessionId);

        String command = CommandUtil.getCommandFromRequest(request);
        AppCommandName appCommand = AppCommandName.fromString(command);


        if (securityContext.canExecute(appCommand)) {
            filterChain.doFilter(servletRequest, servletResponse);

        } else {
            ((HttpServletResponse) servletResponse).sendRedirect("?" + PARAM_COMMAND + "=" +
                    AppCommandName.NO_PERMISSION);
        }
    }

    @Override
    public void destroy() {

    }
}
