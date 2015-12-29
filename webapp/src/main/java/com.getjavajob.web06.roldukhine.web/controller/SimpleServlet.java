package com.getjavajob.web06.roldukhine.web.controller;

import com.getjavajob.web06.roldukhine.entity.user.AccountService;
import com.getjavajob.web06.roldukhine.entity.user.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "SimpleServlet")
public class SimpleServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sessionId = request.getSession().getId();

        AccountService accountService = new AccountService();

        UserProfile profile = new UserProfile("loginValue");
        accountService.addSession(request.getSession().getId(), profile);


        StringBuilder sb = new StringBuilder("<html><head><meta charset=\"UTF-8\"></head><body>");
        sb.append("You are герой!");
        List<String> bookList = new ArrayList<>();
        bookList.add("1 книга");
        bookList.add("2 книга");
        bookList.add("3 книга");
        bookList.add("4 книга");
        bookList.add("5 книга");

        request.setAttribute("bookList", bookList);
        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);

        /*
        Второй способ: без jsp;
        sb.append("<table>");
        for (int i = 0; i < 10; i++) {
            sb.append("<tr><td>");
            sb.append(i);
            sb.append("</td><td>");
            sb.append(Math.pow(2, i));
            sb.append("</td></tr>");
        }
        sb.append("</table></body></html>");
        response.getOutputStream().write(sb.toString().getBytes());
        */
    }
}
