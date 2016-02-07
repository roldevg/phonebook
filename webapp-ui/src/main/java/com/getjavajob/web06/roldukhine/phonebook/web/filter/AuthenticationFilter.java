package com.getjavajob.web06.roldukhine.phonebook.web.filter;

import javax.servlet.*;
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

        HttpSession session = req.getSession(false);
        if (session != null) {
            Object user = session.getAttribute("user");
            if (user != null) {
                chain.doFilter(request, response);
                return;
            }
        }

        String requestURI = ((HttpServletRequest) request).getRequestURI();
        if (requestURI.matches(".*(css|jpg|png|gif|js)")) {
            chain.doFilter(request, response);
            return;
        } else if (requestURI.contains("/account/login")) {
            chain.doFilter(request, response);
            return;
        }

        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    public void destroy() {

    }

}
