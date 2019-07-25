package com.example.demo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends CrudRepository<Book,Long> {
        List<Book> findByNum(Long num);
        List<Book> findByName(String name);
}
