package com.example.demo.services;

import com.example.demo.Role;
import com.example.demo.entity.Book;
import com.example.demo.entity.User;
import com.example.demo.repos.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService{
    @Autowired
    UsersRepository usersRepository;

    public User loadUserByUsername(String s) throws UsernameNotFoundException {
        if(s==null) return null;
        List<User> users= usersRepository.findByUsername(s);
        if(users.size()>0) return users.get(0);
        User user=usersRepository.findByFbId(s);
        if(user==null) user=usersRepository.findByGoogleSub(s);
        return user;
    }
    public void saveUser(User u){
        usersRepository.save(u);
    }
    public boolean addUser(User user){
        List<User> usersFromDb = usersRepository.findByUsername(user.getUsername());


        if (usersFromDb.size() != 0) {
            return false;
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.ADMIN));
        user.setBooks(new ArrayList<>());
        usersRepository.save(user);
        return true;
    }
    public List<Book> getUsersBook(Long id){
        return usersRepository.findById(id).get().getBooks();
    }
    public Iterable<User> getAllUsers(){
        return usersRepository.findAll();
    }
    public void updateUser(User user){
        usersRepository.save(user);
    }

    public void addBookToUser(User user, Book book) {
        boolean ex=user.getBooks().contains(book);
        if(book!=null && !ex){
            user.addBook(book);
            usersRepository.save(user);
        }
    }
}
