package com.example.demo.controller;


import com.example.demo.entity.Book;
import com.example.demo.repos.BooksRepository;
import com.example.demo.entity.User;
import com.example.demo.repos.UsersRepository;
import com.example.demo.services.BookService;
import com.example.demo.services.UserService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/book")
public class BookController {
    final private BookService bookService;
    final private UserService userService;


    BookController(BookService bookService,UserService userService) {
        this.bookService = bookService;
        this.userService = userService;

    }

    @GetMapping
    public ModelAndView getUsersBooks(ModelAndView model, Principal principal) {
        User user=userService.loadUserByUsername(principal.getName());
        model.addObject("book", user.getBooks());
        return model;
    }


    @GetMapping("/filter")
    public ModelAndView filter(@RequestParam String filter, @AuthenticationPrincipal User user, ModelAndView model){
        List<Book> repoBooks=bookService.getBookByNameContaining(filter);
        List<Book> userBooks=user.getBooks();
        List<Book> books=repoBooks.stream().filter(userBooks::contains).collect(Collectors.toList());
        model.addObject("book",books);
        model.addObject("filter",filter);
        model.setViewName("/book");
        return model;
    }
}
