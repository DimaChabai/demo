package com.example.demo;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolver.iterator;

@Controller
@RequestMapping(value = "/book")
public class AdvanceController {
    final BooksRepository booksRepository;
    final UsersRepository usersRepository;
    @Value("${upload.path}")
    private String uploadPath;

    AdvanceController(BooksRepository booksRepository, UsersRepository usersRepository) {
        this.booksRepository = booksRepository;
        this.usersRepository = usersRepository;

    }

    @GetMapping
    public ModelAndView getBook(@RequestParam String id, ModelAndView model) {
        Optional<User> t1;
        t1 = usersRepository.findById(Long.parseLong(id));
        List<Book> boks = t1.get().getBooks();
        List<Book> bookList = new ArrayList<>();
        for (Book iter : boks) {
            bookList.add(booksRepository.findByNum(iter.getNum()).get(0));
        }
        model.setViewName("book");
        model.addObject("id", id);
        model.addObject("book", bookList);
        return model;
    }

    @PostMapping
    private ModelAndView addBookToUser(@RequestParam String id,
                                       @RequestParam String bookname,
                                       @RequestParam("file") MultipartFile file,
                                       ModelAndView model) throws IOException {
        List<Book> books = booksRepository.findByName(bookname);
        User user = usersRepository.findById(Long.parseLong(id)).get();

        if (!books.isEmpty()) {
            if (!user.getBooks().contains(books.get(0)))
                user.addBook(books.get(0));
        } else if (file != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath+"/"+ resultFilename));
            booksRepository.save(new Book( resultFilename,bookname));
            user.addBook(booksRepository.findByName(bookname).get(0));
        }
        usersRepository.save(user);
        getBook(id, model);
        return model;
    }
}
