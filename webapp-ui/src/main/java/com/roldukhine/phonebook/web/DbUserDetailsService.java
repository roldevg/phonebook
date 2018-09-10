package com.roldukhine.phonebook.web;

import com.roldukhine.entity.User;
import com.roldukhine.entity.UserService;
import com.roldukhine.phonebook.web.role.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DbUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public DbUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findByLogin(username)
                .map(this::createUserDetails)
                .get();
    }

    private UserDetails createUserDetails(User foundUser) {
        return org.springframework.security.core.userdetails.User.withUsername(foundUser.getLogin())
                .password(foundUser.getPassword())
                .roles(UserRoles.USER.name())
                .build();
    }
}
