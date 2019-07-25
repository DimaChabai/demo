package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/")
public class IndexController {

    final private UsersRepository usersRepository;

    public IndexController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @RequestMapping( method = RequestMethod.GET)
    public ModelAndView index(ModelAndView model) {
        model.setViewName("index");
        model.addObject("users",usersRepository.findAll());

        return model;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView add(@RequestParam String name, ModelAndView model){
        usersRepository.save(new User(name));

        model.setViewName("index");
        model.addObject("users",usersRepository.findAll());

        return model;
    }

}