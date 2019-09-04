package com.example.demo;

import com.example.demo.entity.User;
import com.example.demo.repos.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
@Component
public class AuthProvider implements AuthenticationProvider {
    @Autowired
    UsersRepository usersRepository;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user= usersRepository.findByUsername(authentication.getName());
        if(user!=null && user.getUsername().equals(authentication.getName())){
            if(!authentication.getPrincipal().equals(user.getPassword())){
                throw new BadCredentialsException("Wrong password");
            }
            Collection<? extends GrantedAuthority> authorities=user.getAuthorities();

            return new UsernamePasswordAuthenticationToken(user,(String)authentication.getCredentials(),authorities);
        }else
            throw new  BadCredentialsException("Username not found");

    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
