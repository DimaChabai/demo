package com.example.demo.entity;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Book")

public class Book implements Serializable {

    @Id
    @GeneratedValue
    private Long num;

    private String filename;
    private String name;

    public Book(){}

    public Book(String filename, String name) {
        this.filename = filename;
        this.name = name;
    }

    @ManyToMany(mappedBy = "books",fetch = FetchType.EAGER)
    private Set<User> users=new HashSet<>();

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Long getNum() {
        return num;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
