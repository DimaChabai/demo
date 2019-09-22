package com.example.demo.controller;

import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController( UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public ModelAndView getUsers(ModelAndView model){
        model.addObject("users",userService.getAllUsers());
        model.setViewName("users");
        return model;
    }
}
