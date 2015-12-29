package com.getjavajob.web06.roldukhine.web.controller.session;

import com.getjavajob.web06.roldukhine.entity.user.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter("user");
        String password = req.getParameter("password");

        UserService userService = new UserService();
        if (userService.isCheckUser(user, password)) {
            HttpSession session = req.getSession();
            session.setAttribute("user", "Pankaj");
            Cookie userName = new Cookie("user", user);
            userName.setMaxAge(30*60);
            resp.addCookie(userName);
            resp.sendRedirect("LoginSuccess.jsp");
        } else {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login/login.jsp");
            PrintWriter out= resp.getWriter();
            out.println("<font color=red>Either user name or password is wrong.</font>");
            rd.include(req, resp);
        }
    }
}
