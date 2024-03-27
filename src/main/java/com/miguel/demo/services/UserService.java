package com.miguel.demo.services;

import com.miguel.demo.repositories.UserRepository;
import com.miguel.demo.user.User;
import com.miguel.demo.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    private void saveUser(User user){
        this.repository.save(user);
    }
    public User createUser(UserDTO user) {
        User newUser = new User(user);
        this.saveUser(newUser);
        return newUser;
    }

    public List<User> getALlUsers() {
        return this.repository.findAll();
    }
}
