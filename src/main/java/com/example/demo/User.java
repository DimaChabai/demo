package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String books= "";
    public User() {
    }

    public void addBook(String bookId){ books+=bookId+", ";}
    public User(String name) {
        this.name = name;
    }

    public User(String name, String booksid) {
        books=booksid;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) &&
                Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }


    public String getBooks() {
        return books;
    }

    public void setBooks(String books) {
        this.books = books;
    }

    public Long getId() {
        System.out.println("getid");
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        System.out.println("getname");
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}