package com.example.demo.controller;

import com.example.demo.BooksRepository;
import com.example.demo.Role;
import com.example.demo.UsersRepository;
import com.example.demo.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.AuthenticationException;
import java.util.Collections;
import java.util.List;

@Controller
public class IndexController {
    final private UsersRepository usersRepository;
    final private BooksRepository booksRepository;

    public IndexController(UsersRepository usersRepository, BooksRepository booksRepository) {
        this.usersRepository = usersRepository;
        this.booksRepository = booksRepository;
    }

    @GetMapping("/")
    public ModelAndView index(ModelAndView model){
        model.setViewName("index");
        return model;
    }
    @GetMapping("/main")
    public ModelAndView main(ModelAndView model) {
        model.setViewName("main");
        model.addObject("books",booksRepository.findAll());
        org.springframework.security.core.userdetails.User  user= (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addObject("user_id",usersRepository.findByUsername(user.getUsername()).get(0).getId());
        return model;
    }

    @PostMapping("/main")
    public ModelAndView add(@RequestParam String username, ModelAndView model){
        usersRepository.save(new User(username));
        model.setViewName("main");
        model.addObject("users",usersRepository.findAll());
        return model;
    }
    @GetMapping("/registration")
    public ModelAndView registration(ModelAndView model){
        model.setViewName("registration");
        return model;
    }

    @PostMapping("/registration")
    public ModelAndView addUser(User user, ModelAndView model){
        List<User> users= usersRepository.findByUsername(user.getUsername());

        System.out.println("sdqwdsadqwd");
        if(!users.isEmpty()){
            model.addObject("message","User exists!");
            model.setViewName("registration");
            return model;
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.ADMIN));
        usersRepository.save(user);
        model.setViewName( "redirect:/login");
        return model;
    }

}