package com.getjavajob.web06.roldukhine.phonebook.web.filter;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticationFilter implements Filter {

    public void init(FilterConfig fConfig) throws ServletException {
        ServletContext context = fConfig.getServletContext();
        context.log("AuthenticationFilter initialized");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String requestURI = ((HttpServletRequest) request).getRequestURI();
        if (requestURI.matches(".*(css|jpg|png|gif|js)")) {
            chain.doFilter(request, response);
            return;
        } else if (requestURI.contains("login")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        if (session != null) {
            Object user = session.getAttribute("login");
            if (user != null) {
                chain.doFilter(request, response);
                return;
            }
        } else {
            session = req.getSession(true);
            Cookie[] cookies = ((HttpServletRequest) request).getCookies();

            String loginCookie = null;
            String passwordCookie = null;
            if (cookies != null) {
                for (int i = 0; i < cookies.length; i++) {
                    Cookie cookie = cookies[i];
                    if (cookie.getName().equals("login")) {
                        loginCookie = cookie.getValue();
                    } else if (cookie.getName().equals("password")) {
                        passwordCookie = cookie.getValue();
                    }
                }

                if (loginCookie != null && passwordCookie != null) {
                    session.setAttribute("login", loginCookie);
                    session.setAttribute("password", passwordCookie);
                    chain.doFilter(request, response);
                    return;
                }
            }
        }

        ((HttpServletResponse) response).sendRedirect("/account/login");
    }

    public void destroy() {

    }

}
