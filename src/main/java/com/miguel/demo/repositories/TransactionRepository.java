package com.miguel.demo.repositories;

import com.miguel.demo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<User, Long> {
}
