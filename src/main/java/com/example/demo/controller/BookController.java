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
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    @GetMapping
    public ModelAndView getUsersBooks(@RequestParam(required = false) String filter,ModelAndView model, @AuthenticationPrincipal User user) {
        List<Book> books1=userService.loadUserByUsername(user.getUsername()).getBooks();
        List<Book> books=user.getBooks();
        if(filter!=null && !filter.isEmpty()) {
            List<Book> userBooks = user.getBooks();
            books = userBooks.stream().filter((v) -> v.getName().contains(filter)).collect(Collectors.toList());
            model.addObject("filter", filter);
        }
        model.addObject("books", books);
        return model;
    }



    @GetMapping("/delFromList")
    public ModelAndView delBookFromList(@RequestParam Long bookId, @AuthenticationPrincipal User user,ModelAndView model){
        List<Book> books=user.getBooks();
        Book book=bookService.getBookById(bookId);
        books.removeIf((v)-> Objects.equals(v.getNum(), bookId));
        user.setBooks(books);
        userService.saveUser(user);
        User usr=userService.loadUserByUsername(user.getUsername());
        usr.setBooks(Collections.emptyList());
        userService.saveUser(usr);
        model.addObject("books",books);
        model.setViewName("/book");
        return model;
    }
}
