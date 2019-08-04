package com.example.demo.controller;


import com.example.demo.entity.Book;
import com.example.demo.BooksRepository;
import com.example.demo.entity.User;
import com.example.demo.UsersRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping(value = "/book")
public class BookController {
    final BooksRepository booksRepository;
    final UsersRepository usersRepository;
    @Value("${upload.path}")
    private String uploadPath;

    BookController(BooksRepository booksRepository, UsersRepository usersRepository) {
        this.booksRepository = booksRepository;
        this.usersRepository = usersRepository;

    }

    @GetMapping
    public ModelAndView getBook(@RequestParam("user_id") User user, ModelAndView model) {
        List<Book> boks =user.getBooks();
        List<Book> bookList = new ArrayList<>();
        for (Book iter : boks) {
            bookList.add(booksRepository.findByNum(iter.getNum()).get(0));
        }
        model.setViewName("book");
        model.addObject("id", user.getId());
        model.addObject("book", bookList);
        return model;
    }

    @PostMapping
    private ModelAndView addBookToUser(@RequestParam("id") User user,
                                       @RequestParam String bookname,
                                       @RequestParam("file") MultipartFile file,
                                       ModelAndView model) throws IOException {

        List<Book> books = booksRepository.findByName(bookname);
        if (!books.isEmpty()) {
            if (!user.getBooks().contains(books.get(0)))
                user.addBook(books.get(0));
        } else if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            booksRepository.save(new Book(resultFilename, bookname));
            user.addBook(booksRepository.findByName(bookname).get(0));
        }
        usersRepository.save(user);
        List<Book> b=user.getBooks();
        List<Book> ob=new ArrayList<>(b);
        model.addObject("book",ob);
        model.addObject("id",user.getId());
        return model;
    }
}
