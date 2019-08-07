package com.example.demo.controller;

import com.example.demo.repos.UsersRepository;
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
    private UsersRepository usersRepository;

    public UserController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
    @GetMapping
    public ModelAndView getUsers(ModelAndView model){
        model.addObject("users",usersRepository.findAll());
        model.setViewName("users");
        return model;
    }
}