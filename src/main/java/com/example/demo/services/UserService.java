package com.example.demo.services;

import com.example.demo.repos.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserService{

    UsersRepository usersRepository;

    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
