package com.shyam.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shyam.dto.EmailDTO;
import com.shyam.dto.HRDTO;
import com.shyam.dto.LoginDTO;
import com.shyam.dto.PasswordDTO;
import com.shyam.dto.UserDTO;
import com.shyam.entities.UserEntity;
import com.shyam.services.EmailService;
import com.shyam.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final UserService userService;
    private final EmailService emailService;

    @GetMapping("/login")
    public String login(@ModelAttribute("loginDto") LoginDTO loginDTO) {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerUser(@ModelAttribute("userDto") UserDTO userDTO) {
        return "auth/register";
    }

    @PostMapping("/register")
    public String processRegisterUser(
        @Valid @ModelAttribute("userDto") UserDTO user, 
        BindingResult result,
        HttpSession session
    ) {
        if (result.hasErrors()) 
            return "auth/register";

        UserEntity checkUser = userService.getByEmail(user.getEmail());
        if (checkUser != null) {
            result.rejectValue("email", "error.email", "Email already registered us");
            return "auth/register";
        }

        else if(!user.getPassword().equals(user.getReenterPassword())) {
            result.rejectValue("reenterPassword", "error.reenterPassword", "Password and conform password must match");
            return "auth/register";
        }
        
        UserEntity registeredUser = userService.registerUser(user);
        emailService.sendActivationEmail(registeredUser);

        session.setAttribute("userAdded", true);
        return "redirect:/auth/login";
    }

    @GetMapping("/register-hr")
    public String registerHR(@ModelAttribute("userDto") HRDTO userDTO) {
        return "auth/register-hr";
    }

    @PostMapping("/register-hr")
    public String processRegisterHR(
        @Valid @ModelAttribute("userDto") HRDTO user, 
        BindingResult result,
        HttpSession session
    ) {
        if (result.hasErrors()) 
            return "auth/register-hr";

        UserEntity checkUser = userService.getByEmail(user.getEmail());
        if (checkUser != null) {
            result.rejectValue("email", "error.email", "Email already registered us");
            return "auth/register-hr";
        }

        else if(!user.getPassword().equals(user.getReenterPassword())) {
            result.rejectValue("reenterPassword", "error.reenterPassword", "Password and conform password must match");
            return "auth/register-hr";
        }
        
        UserEntity registeredUser = userService.registerHR(user);
        emailService.sendActivationEmail(registeredUser);

        session.setAttribute("userAdded", true);
        return "redirect:/auth/login";
    }

    @GetMapping("/activate-user/{token}")
    public String activateAccount(
        @PathVariable("token") String token,
        HttpSession session
    ) {
        int activate = userService.activateAccount(token);

        if (activate == 0) 
            session.setAttribute("activate", "User account activated successfully");

        return "redirect:/auth/login";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(@ModelAttribute("emailDTO") EmailDTO emailDTO) {
        return "auth/forgot-password";
    }
    
    @PostMapping("/forgot-password")
    public String processForgotPassword(
        @Valid @ModelAttribute("emailDTO") EmailDTO emailDTO,
        BindingResult result,
        HttpSession session
    ) {
        if (result.hasErrors()) 
            return "auth/forgot-password";

        UserEntity user = userService.getByEmail(emailDTO.getEmail());
        if (user == null) {
            result.rejectValue("email", "error.email", "Email is not registered with us");
            return "auth/forgot-password";
        }
        
        emailService.sendForgotPasswordEmail(user);
        session.setAttribute("forgotEmail", "Password reset mail send to your mail. check your inbox");
        return "redirect:/auth/login";
    }

    @GetMapping("/validate-token/{token}")
    public String setPassword(
        @PathVariable("token") String token,
        HttpSession session
    ) {
        UserEntity byToken = userService.validateToken(token);

        if (byToken == null) {
            return "redirect:/auth/login";
        }
        
        session.setAttribute("reqUser", byToken);
        return "redirect:/auth/set-password";
    }

    @GetMapping("/set-password")
    public String setPassword(
        @ModelAttribute("passwordDTO") PasswordDTO passwordDTO,
        HttpSession session
    ) {
        UserEntity user = (UserEntity) session.getAttribute("reqUser");
        if (user == null) {
            return "error/403";
        }

        return "auth/set-password";
    }
    
    @PostMapping("/set-password")
    public String processSetPassword(
        @Valid @ModelAttribute("passwordDTO") PasswordDTO passwordDTO,
        BindingResult result,
        HttpSession session
    ) {
        if (result.hasErrors()) 
            return "auth/set-password";

        if (!passwordDTO.getPassword().equals(passwordDTO.getReenterPassword())) {
            result.rejectValue("reenterPassword", "error.reenterPassword", "Both Passwords must match");
            return "auth/set-password";
        }
        
        UserEntity user = (UserEntity) session.getAttribute("reqUser");
        
        userService.setPassword(user, passwordDTO.getPassword());
        session.setAttribute("setPassword", "Password reset successfully");
        session.setAttribute("reqUser", null);
        return "redirect:/auth/login";
    }
    

}
