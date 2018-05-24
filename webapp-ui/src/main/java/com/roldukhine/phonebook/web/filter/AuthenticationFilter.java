package com.roldukhine.phonebook.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
public class AuthenticationFilter implements Filter {

    private static final String FILES_WITHOUT_FILTER = ".*(css|jpg|png|gif|js)";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String LOGIN_PAGE_URL = "/account/login";

    public void init(FilterConfig filterConfig) {
        logger.debug("init AuthenticationFilter FilterConfig {}", filterConfig);
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        logger.debug("doFilter request {}, response {}, chain {}", request, response, chain);

        HttpServletRequest req = (HttpServletRequest) request;

        String requestUri = ((HttpServletRequest) request).getRequestURI();
        if (requestUri.matches(FILES_WITHOUT_FILTER)) {
            logger.debug("requestURI matches files without filter");
            chain.doFilter(request, response);
            return;
        } else if (requestUri.contains(LOGIN)) {
            logger.debug("requestURI contains login page url");
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        if (session != null) {
            logger.debug("session not null");
            Object user = session.getAttribute(LOGIN);
            logger.debug("user from login attribute {}", user);
            if (user != null) {
                chain.doFilter(request, response);
                return;
            }
        } else {
            session = req.getSession(true);
            logger.debug("create session");

            Cookie[] cookies = ((HttpServletRequest) request).getCookies();
            logger.debug("cookies {}", Arrays.toString(cookies));

            String loginCookie = null;
            String passwordCookie = null;
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (LOGIN.equals(cookie.getName())) {
                        logger.debug("cookie equals login");
                        loginCookie = cookie.getValue();
                    } else if (PASSWORD.equals(cookie.getName())) {
                        logger.debug("cookie equals password");
                        passwordCookie = cookie.getValue();
                    }
                }

                if (loginCookie != null && passwordCookie != null) {
                    logger.debug("add attribute to session {}, {}", loginCookie, passwordCookie);
                    session.setAttribute(LOGIN, loginCookie);
                    session.setAttribute(PASSWORD, passwordCookie);
                    chain.doFilter(request, response);
                    return;
                }
            }
        }

        logger.debug("loginPageUrlRedirect {}", LOGIN_PAGE_URL);
        ((HttpServletResponse) response).sendRedirect(LOGIN_PAGE_URL);
    }

    public void destroy() {
        logger.debug("destroy AuthenticationFilter");
    }

}
