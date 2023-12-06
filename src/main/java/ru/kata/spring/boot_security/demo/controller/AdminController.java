package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;



@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping()
    public String printUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "adminPanel";
    }

    @GetMapping("/check-user")
    public String showUser(Model model, @RequestParam(name = "id") Long id) {
        model.addAttribute("user", userService.findById(id));
        return "adminUserPage";
    }

    @GetMapping("/user-add")
    public String addUserForm(Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user", new User());
        return "user-add";
    }

    @PostMapping("/user-add")
    public String addUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleService.getAllRoles());
            return "user-add";
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/user-update")
    public String updateUserForm(@RequestParam(name = "id") Long id, Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user", userService.findById(id));
        return "user-update";
    }

    @PostMapping("/user-update")
    public String updateUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        if (bindingResult.hasErrors()) {
            return "user-update";
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/user-delete")
    public String deleteUserFromTable(@RequestParam(name = "id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            request.getSession().invalidate();
        }
        return "redirect:/";
    }

}