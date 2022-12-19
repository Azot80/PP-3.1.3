package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/index")
    public String showHome(Principal principal, Model model) {
        model.addAttribute("usersList", userService.getAllUsers());
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "/admin/index";
    }

    @GetMapping("/{id}/showFormForEdit")
    public String showFormForEdit(@PathVariable("id") long id, Model model) {
        User userToEdit = userService.getUserById(id);
        model.addAttribute("editUser", userToEdit);
        model.addAttribute("roles", roleService.listRoles());
        return "/admin/user-form";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("editUser") User user, @PathVariable("id") long id) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/admin/index";
    }

    @GetMapping("{id}/delete")
    public String deleteUser(@PathVariable("id") long id) {
        userService.removeUserById(id);
        return "redirect:/admin/index";
    }
}
