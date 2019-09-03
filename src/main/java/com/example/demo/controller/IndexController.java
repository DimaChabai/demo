package com.example.demo.controller;

import com.example.demo.dto.CaptchaResponseDto;
import com.example.demo.entity.Book;
import com.example.demo.repos.BooksRepository;
import com.example.demo.Role;
import com.example.demo.repos.UsersRepository;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@Controller
public class IndexController {
    final private UsersRepository usersRepository;
    final private BooksRepository booksRepository;

    private final String CAPTHCA_URL="https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Value("${recaptcha.secret}")
    private String secret;

    @Autowired
    private RestTemplate restTemplate;

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
    public ModelAndView addUser(User user,
                                ModelAndView model,
                                @RequestParam("g-recaptcha-response") String captchaResponse ){
        List<User> users= usersRepository.findByUsername(user.getUsername());
        String url=String.format(CAPTHCA_URL,secret,captchaResponse);
        CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);

        if(!response.isSuccess()){
            model.addObject("captchaError","FillCapthca");
        }
        if(!users.isEmpty()){
            model.addObject("message","User exists!");

        }
        if(!response.isSuccess() && !users.isEmpty()){
            model.setViewName("registration");
            return model;
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        usersRepository.save(user);
        model.setViewName( "redirect:/login");
        return model;
    }
    @GetMapping("/add")
    public ModelAndView addBook(@RequestParam String num, @RequestParam String user_id, ModelAndView model){
        User curUser=usersRepository.findById(Long.parseLong(user_id)).get();
        Book book=booksRepository.findByNum(Long.parseLong(num)).get(0);
        if(!curUser.getBooks().contains(book))
        curUser.addBook(book);
        usersRepository.save(curUser);
        model.setViewName("redirect:/main");

        return model;
    }
}