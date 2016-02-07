package com.getjavajob.web06.roldukhine.phonebook.web.controller;

import com.getjavajob.web06.roldukhine.entity.User;
import com.getjavajob.web06.roldukhine.entity.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(@RequestParam String login,
                              @RequestParam String password,
                              HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView("login");
        User checkUser = userService.checkUser(login, password);
        if (checkUser == null) {
            return modelAndView;
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("user", checkUser);
        }

        return new ModelAndView("redirect:/employee/getAll");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        session.setAttribute("user", null);
        session.invalidate();
        return "redirect:/account/login";
    }
}
