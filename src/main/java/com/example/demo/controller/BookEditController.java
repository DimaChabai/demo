package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.repos.BooksRepository;
import com.example.demo.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/edit")
@PreAuthorize("hasAuthority('ADMIN')")
public class BookEditController {
    @Autowired
    BookService bookService;

    public BookEditController(BookService bookService) {
        this.bookService = bookService;
    }
    @GetMapping
    private ModelAndView editBookGet(ModelAndView model){
        Iterable<Book> books=bookService.getAllBooks();
        model.addObject("book",books);
        model.setViewName("edit");
        return model;
    }

    @PostMapping
    private ModelAndView editBookPost(@RequestParam String num,@RequestParam String newName ,ModelAndView model){
        if(bookService.getBookByName(newName)==null) {
            Book book = bookService.getBookById(Long.parseLong(num));
            bookService.updateBook(book,newName);
        }else{
            model.addObject("message","Имя занято");
        }
        model.setViewName("redirect:/edit");
        return model;
    }

}
