package com.example.demo;

import javax.persistence.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="user")

public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @JoinTable(
            name="book_user",
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "book_id", referencedColumnName = "num")})
    @ManyToMany
    private List<Book> books;

    public User() {
        books = new ArrayList<>();
    }

    public void addBook(Book bookId){ books.add(bookId);}
    public User(String name) {
        this.name = name;
        books = new ArrayList<>();
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


    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}