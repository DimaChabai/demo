package com.example.demo;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/book")
public class AdvanceController {
    BooksRepository booksRepository;

    AdvanceController(BooksRepository booksRepository){
        this.booksRepository=booksRepository;
    }

    @GetMapping
    public ModelAndView getBook(@RequestParam(defaultValue = "0") String id , ModelAndView model){
        Iterable<Book> t;
        if(id.equals("0")) t=booksRepository.findAll();
        else
        t=booksRepository.findByNum(Long.parseLong(id));
        model.setViewName("book");
        model.addObject("book",t);
        return model;
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    private ModelAndView addBook(@RequestParam String name,ModelAndView model){
        booksRepository.save(new Book(name));
        model.setViewName("addBook");
        model.addObject("books",booksRepository.findAll());
        return model;
    }
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    private ModelAndView add(ModelAndView model){
        model.setViewName("addBook");
        model.addObject("books",booksRepository.findAll());
        return model;
    }
}
