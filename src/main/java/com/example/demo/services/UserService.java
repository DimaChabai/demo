package com.example.demo.services;

import com.example.demo.Role;
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

    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDetails userDetails= usersRepository.findByUsername(s);
        if(userDetails==null) throw new UsernameNotFoundException("Username not found");
        return userDetails;
    }
    public void saveUser(User u){
        usersRepository.save(u);
    }
    public boolean addUser(User user){
        User userFromDb = usersRepository.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return false;
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setBooks(new ArrayList<>());
        usersRepository.save(user);
        return true;
    }
    public Iterable<User> getAllUser(){
        return usersRepository.findAll();
    }
    public void updateUser(User user){
        usersRepository.save(user);
    }

}
