package com.example.demo.repos;

import com.example.demo.entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends CrudRepository<Book,Long> {
    Book findByNum(Long num);
    Book findByName(String name);
    List<Book> findByNameContaining(String name);
}
