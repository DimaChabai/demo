package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.repos.BooksRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/edit")
public class BookEditController {
    BooksRepository booksRepository;

    public BookEditController(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }
    @GetMapping
    private ModelAndView editBookGet(@RequestParam String num,ModelAndView model){
        Book book=booksRepository.findByNum(Long.parseLong(num)).get(0);
        model.addObject("num",book.getNum());
        model.addObject("name",book.getName());
        model.setViewName("edit");
        return model;
    }

    @PostMapping
    private ModelAndView editBookPost(@RequestParam String num,@RequestParam String newName ,ModelAndView model){
        if(booksRepository.findByName(newName).isEmpty()) {
            List<Book> bookList = booksRepository.findByNum(Long.parseLong(num));
            Book book = bookList.get(0);
            book.setName(newName);
            booksRepository.save(book);
        }else{
            model.addObject("message","Имя занято");
        }

        model.setViewName("redirect:/main");

        return model;
    }

}
