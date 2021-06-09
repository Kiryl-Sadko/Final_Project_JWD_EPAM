package by.sadko.training.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import static by.sadko.training.util.AppConstants.PARAM_LANGUAGE;

/**
 * Language filter
 *
 * @author Sadko Kiryl
 * @version 1.0
 */
@WebFilter(servletNames = "index", filterName = "lang_filter")
public class LangFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * Setting language to cookie
     *
     * @param request  - request
     * @param response - response
     * @param chain    - filter chain
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (request instanceof HttpServletRequest) {

            String lang = request.getParameter(PARAM_LANGUAGE);
            HttpServletRequest httpRequest = (HttpServletRequest) request;

            if (("en".equalsIgnoreCase(lang) || "ru".equalsIgnoreCase(lang))) {

                Cookie langCookie = new Cookie(PARAM_LANGUAGE, lang);
                langCookie.setPath(httpRequest.getContextPath());
                httpRequest.setAttribute(PARAM_LANGUAGE, lang);
                ((HttpServletResponse) response).addCookie(langCookie);

            } else {
                Optional<Cookie[]> cookies = Optional.ofNullable(httpRequest.getCookies());

                Cookie langCookie = cookies.map(Stream::of)
                        .orElse(Stream.empty())
                        .filter(cookie -> cookie.getName().equalsIgnoreCase(PARAM_LANGUAGE))
                        .findFirst()
                        .orElse(new Cookie(PARAM_LANGUAGE, "en"));

                langCookie.setPath(httpRequest.getContextPath());
                httpRequest.setAttribute(PARAM_LANGUAGE, langCookie.getValue());
                ((HttpServletResponse) response).addCookie(langCookie);
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
