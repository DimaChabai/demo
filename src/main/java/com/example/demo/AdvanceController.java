package com.example.demo;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolver.iterator;

@Controller
@RequestMapping(value = "/book")
public class AdvanceController {
    BooksRepository booksRepository;
    final UsersRepository usersRepository;

    AdvanceController(BooksRepository booksRepository, UsersRepository usersRepository) {
        this.booksRepository = booksRepository;
        this.usersRepository = usersRepository;

    }

    @GetMapping
    public ModelAndView getBook(@RequestParam String id, ModelAndView model) {
        Optional<User> t1;
        t1 = usersRepository.findById(Long.parseLong(id));
        String[] boks = t1.get().getBooks().split(", ");
        List<Book> bookList = new ArrayList<>();
        if (!boks[0].equals(""))
            for (String iter : boks) {
                bookList.add(booksRepository.findByNum(Long.parseLong(iter)).get(0));
            }
        model.setViewName("book");
        model.addObject("id", id);
        model.addObject("book", bookList);
        return model;
    }

    @RequestMapping(method = RequestMethod.POST)
    private ModelAndView addBookToUser(@RequestParam String id, @RequestParam String bookname, ModelAndView model) {
        List<Book> books = booksRepository.findByName(bookname);
        User user = usersRepository.findById(Long.parseLong(id)).get();

        if (!books.isEmpty()) {
            if (!user.getBooks().contains(books.get(0).getNum().toString()))
                user.addBook(books.get(0).getNum().toString());
        } else {
            booksRepository.save(new Book(bookname));
            user.addBook(booksRepository.findByName(bookname).get(0).getNum().toString());
        }

        usersRepository.save(user);

        getBook(id, model);

        return model;
    }
}
