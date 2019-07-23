package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    final UsersRepository usersRepository;

    public ApiController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping("/visits")
    public Iterable<User> getVisits() {
        return usersRepository.findAll();
    }
}