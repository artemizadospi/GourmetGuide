package com.pweb.gourmetguide.controller;

import com.pweb.gourmetguide.dtos.LoginRequest;
import com.pweb.gourmetguide.dtos.LoginResponse;
import com.pweb.gourmetguide.dtos.SignUpDTO;
import com.pweb.gourmetguide.model.User;
import com.pweb.gourmetguide.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public SignUpDTO addUser(@RequestBody SignUpDTO signUpDTO) {
        return userService.addUser(signUpDTO);
    }
}
