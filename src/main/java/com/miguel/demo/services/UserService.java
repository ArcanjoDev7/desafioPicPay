package com.miguel.demo.services;

import com.miguel.demo.domain.user.UserType;
import com.miguel.demo.repositories.UserRepository;
import com.miguel.demo.domain.user.User;
import com.miguel.demo.domain.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public void saveUser(User user){
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

    public User findUserById(Long id) throws Exception {
        return this.repository.findById(id).orElseThrow(() -> new Exception("Usuario não encontrado"));
    }

    public Boolean validateUser(User payer, BigDecimal amout) throws Exception {

        if(payer.getUserType() == UserType.MERCHANT){
            throw  new Exception("Um usuario Lojista não pode realizar transações");
        }

        if(payer.getBalance().compareTo(amout) < 0){
            throw  new Exception("Saldo insuficiente");
        }
        return true;
    }
}
