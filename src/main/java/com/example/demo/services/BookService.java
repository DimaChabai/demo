package com.example.demo.services;

import com.example.demo.entity.Book;
import com.example.demo.repos.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService  {
    @Autowired
    BooksRepository booksRepository;
    public Iterable<Book> getAllBooks(){
        return booksRepository.findAll();
    }
    public Book getBookById(Long id){

        return booksRepository.findByNum(id);
    }
    public Book getBookByName(String name){
        return booksRepository.findByName(name);
    }
    public void addBook(Book book){
        booksRepository.save(book);
    }
    public List<Book> getBookByNameContaining(String name){
        return booksRepository.findByNameContaining(name);
    }
    public void updateBook(Book book, String name){
        book.setName(name);
        booksRepository.save(book);
    }

}

