package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;

@Controller
public class IndexController {


    final private UsersRepository usersRepository;

    public IndexController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping("/")
    public ModelAndView index(ModelAndView model){
        model.setViewName("index");
        return model;
    }
    @GetMapping("/main")
    public ModelAndView main(ModelAndView model) {
        model.setViewName("main");
        model.addObject("users",usersRepository.findAll());
        return model;
    }

    @PostMapping("/main")
    public ModelAndView add(@RequestParam String name, ModelAndView model){
        usersRepository.save(new User(name));
        model.setViewName("main");
        model.addObject("users",usersRepository.findAll());
        return model;
    }
    @GetMapping("/registration")
    public ModelAndView regis(ModelAndView model){
        model.setViewName("registration");
        return model;
    }

    @PostMapping("/registration")
    public ModelAndView addUser(User user, ModelAndView model){
        User userdb = usersRepository.findByUsername(user.getUsername()).get(0);
        System.out.println("sdqwdsadqwd");
        if(userdb!=null){
            model.addObject("message","User exists!");
            model.setViewName("registration");
            return model;
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        usersRepository.save(user);
        model.setViewName("registration");
        return model;
    }

}