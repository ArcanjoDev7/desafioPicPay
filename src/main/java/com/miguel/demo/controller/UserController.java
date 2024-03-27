package com.miguel.demo.controller;

import com.miguel.demo.services.UserService;
import com.miguel.demo.domain.user.User;
import com.miguel.demo.domain.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO user){
        User newUser = service.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>>getALlUsers(){
        var users = this.service.getALlUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
