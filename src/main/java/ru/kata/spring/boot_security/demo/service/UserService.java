package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> findAll();

    User findById(Long id);

    void saveUser(User user);

    void deleteById(Long id);

    void update(Long id, User updatedUser);

    User findByUsername(String username);
}
