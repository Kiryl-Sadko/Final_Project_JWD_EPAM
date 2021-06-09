package by.sadko.training.filter;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Encoding filter
 *
 * @author Sadko Kiryl
 * @version 1.0
 */
@WebFilter(servletNames = "index", filterName = "encoding_filter")
public class CharacterEncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * Setting UTF-8 encoding to request
     *
     * @param servletRequest  - request
     * @param servletResponse - response
     * @param filterChain     - filter chain
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        String characterEncoding = servletRequest.getCharacterEncoding();
        String utf8 = StandardCharsets.UTF_8.name();

        if (!utf8.equalsIgnoreCase(characterEncoding)) {

            servletRequest.setCharacterEncoding(utf8);
            servletResponse.setContentType("text/html; charset=UTF-8");
            servletResponse.setCharacterEncoding("UTF-8");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
