package com.example.demo.controller;


import com.example.demo.dto.CaptchaResponseDto;
import com.example.demo.entity.Book;
import com.example.demo.entity.User;
import com.example.demo.services.BookService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Controller
public class IndexController {

    @Value("${upload.path}")
    private String uploadPath;
    @Autowired
    private final BookService bookService;
    @Autowired
    private final UserService userService;
    private final String CAPTHCA_URL="https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Value("${recaptcha.secret}")
    private String secret;

    @Autowired
    private RestTemplate restTemplate;

    public IndexController(BookService bookService, UserService userService) {
        this.userService=userService;
        this.bookService=bookService;
    }

    @GetMapping("/")
    public ModelAndView index(ModelAndView model){

        model.setViewName("index");
        return model;
    }
    @GetMapping("/main")
    public ModelAndView main(ModelAndView model) {
        model.addObject("books",bookService.getAllBooks());
        model.setViewName("main");
        return model;
    }

    @PostMapping("/main")
    private ModelAndView addBook(Principal usr,
                                 @RequestParam String bookName,
                                 @RequestParam("file") MultipartFile file,
                                 ModelAndView model) throws IOException {
        User user=userService.loadUserByUsername(usr.getName());
        Book book = bookService.getBookByName(bookName);
        if (book!= null) {
            if (!user.getBooks().contains(book))
                user.addBook(book);
        } else if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            bookService.addBook(new Book(resultFilename, bookName));
            user.addBook(bookService.getBookByName(bookName));
        }
        userService.updateUser(user);
        List<Book> b=user.getBooks();
        model.setViewName("book");
        model.addObject("book",b);
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
            model.addObject("message","FillCapthca");
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
    public ModelAndView addBook(@PathVariable Book book, Principal principal, ModelAndView model){
        userService.addBookToUser(userService.loadUserByUsername(principal.getName())
                ,bookService.getBookByName(book.getName()));
        model.setViewName("redirect:/main");

        return model;
    }
}