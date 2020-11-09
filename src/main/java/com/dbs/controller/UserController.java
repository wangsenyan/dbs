package com.dbs.controller;

import com.dbs.entry.User;
import com.dbs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable("id") Integer id) {
        User user = userRepository.findById(id).get();//非空才行
        return user;
    }

    @GetMapping("/user")
    public User insertUser(User user) {
        User u = userRepository.save(user);
        return u;
    }
}
