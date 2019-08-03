package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

}