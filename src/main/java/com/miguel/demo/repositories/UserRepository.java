package com.miguel.demo.repositories;

import com.miguel.demo.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long> {
}
