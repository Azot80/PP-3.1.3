package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

public interface UserService {
    User getUserById(Long id);
    void saveUser(User user);
    void removeUserById(Long id);
    List<User> getAllUsers();
    User findByUsername(String username);
}
