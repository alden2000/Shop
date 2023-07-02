package com.prodavnica.shop2.user.contriller;



import com.prodavnica.shop2.user.entity.User;
import com.prodavnica.shop2.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @Repository, @Service, @Controller, @RestController
 *
 * Kontroler -> service -> repository,entity
 *   |
 *   v
 *  model -> users ..ime: korisnici
 *  view: index
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String getClock() {
        return "index";
    }

    //http://localhost:8080
    @GetMapping("/UserManagement")
    public String getUsers(Model model){
        List<User> users = userService.getAllUsers();
        model.addAttribute("korisnici", users);
        return "UserManagement";
    }
    //http://localhost:8080/login
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    //http://localhost:8081/register
    @GetMapping("/register")
    public String showNewUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user){
        try {
            String plainPassword = user.getPassword();
            String hashedPassword = passwordEncoder.encode(plainPassword);
            user.setPassword(hashedPassword);
            user.setRole("USER");
            userService.saveUser(user);
            return "redirect:/";
        } catch (Exception e) {
            return "redirect:/register?error=Bad registration";
        }
    }
}
