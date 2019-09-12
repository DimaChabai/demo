package com.example.demo.controller;


import com.example.demo.*;


import com.example.demo.dto.CaptchaResponseDto;
import com.example.demo.entity.Book;
import com.example.demo.entity.User;
import com.example.demo.repos.BooksRepository;
import com.example.demo.repos.UsersRepository;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;

@Controller
public class IndexController {

    @Autowired
    private UsersRepository usersRepository;
    final private BooksRepository booksRepository;
    @Autowired
    private UserService userService;
    private final String CAPTHCA_URL="https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Value("${recaptcha.secret}")
    private String secret;

    @Autowired
    private RestTemplate restTemplate;

    public IndexController(UsersRepository usersRepository, BooksRepository booksRepository) {

        this.booksRepository = booksRepository;
    }

    @GetMapping("/")
    public ModelAndView index(ModelAndView model){

        model.setViewName("index");
        return model;
    }
    @GetMapping("/main")
    public ModelAndView main(ModelAndView model, Principal principal) {
        model.setViewName("main");
        model.addObject("books",booksRepository.findAll());
        model.addObject("user_id",userService.loadUserByUsername(principal.getName()));
        return model;
    }

    @PostMapping("/main")
    public ModelAndView add(@RequestParam String username, ModelAndView model){
        userService.addUser(new User(username));
        model.setViewName("main");
        model.addObject("users",userService.getAllUser());
        return model;
    }
    @GetMapping("/registration")
    public ModelAndView registration(ModelAndView model){
        model.setViewName("registration");
        return model;
    }

    @PostMapping("/registration")
    public ModelAndView addUser(User user,
                                ModelAndView model,
                                @RequestParam("g-recaptcha-response") String captchaResponse ){

        String url=String.format(CAPTHCA_URL,secret,captchaResponse);
        CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);

        if(!response.isSuccess()){
            model.addObject("captchaError","FillCapthca");
        }

        if(!response.isSuccess()){
            model.setViewName("registration");
            return model;
        }

        if(!userService.addUser(user)) {
            model.setViewName("registration");
            return model;
        }
        model.setViewName( "redirect:/login");
        return model;
    }
    @GetMapping("/add/{book}")
    public ModelAndView addBook(@PathVariable Book book, @AuthenticationPrincipal User user, ModelAndView model){

        if(!user.getBooks().contains(book))
        user.addBook(book);
        userService.updateUser(user);
        model.setViewName("redirect:/main");

        return model;
    }
}