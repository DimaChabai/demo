package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

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
    public ModelAndView add(@RequestParam String username, ModelAndView model){
        usersRepository.save(new User(username));
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
        List<User> users= usersRepository.findByUsername(user.getUsername());

        System.out.println("sdqwdsadqwd");
        if(!users.isEmpty()){
            model.addObject("message","User exists!");
            model.setViewName("registration");
            return model;
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        usersRepository.save(user);
        model.setViewName( "redirect:/login");
        return model;
    }

}