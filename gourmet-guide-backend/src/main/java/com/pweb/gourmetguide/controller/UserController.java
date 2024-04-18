package com.pweb.gourmetguide.controller;

import com.pweb.gourmetguide.dtos.LoginRequest;
import com.pweb.gourmetguide.dtos.LoginResponse;
import com.pweb.gourmetguide.dtos.SignUpRequest;
import com.pweb.gourmetguide.model.User;
import com.pweb.gourmetguide.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController()
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @PostMapping("/signup")
    public User addUser(@RequestBody SignUpRequest signUpRequest) {
        return userService.addUser(signUpRequest);
    }
}
