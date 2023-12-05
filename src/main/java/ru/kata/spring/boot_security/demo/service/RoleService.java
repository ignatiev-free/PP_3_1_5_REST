package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entity.Roles;

import java.util.List;

public interface RoleService {
    List<Roles> getAllRoles();
}