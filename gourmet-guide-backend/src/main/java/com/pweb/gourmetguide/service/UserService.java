package com.pweb.gourmetguide.service;

import com.pweb.gourmetguide.dtos.LoginRequest;
import com.pweb.gourmetguide.dtos.LoginResponse;
import com.pweb.gourmetguide.dtos.SignUpRequest;
import com.pweb.gourmetguide.exception.InvalidCredentialsException;
import com.pweb.gourmetguide.exception.UserNotFoundException;
import com.pweb.gourmetguide.model.Role;
import com.pweb.gourmetguide.model.User;
import com.pweb.gourmetguide.security.CustomUserDetails;
import com.pweb.gourmetguide.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.pweb.gourmetguide.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final JWTUtils jwtUtils;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest loginRequest) {
        BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(loginRequest.getUsername());
        User loginUser = getByUsername(loginRequest.getUsername());
        boolean validPassword = bcryptEncoder.matches(loginRequest.getPassword(), loginUser.getPassword());
        if (!validPassword) {
            throw new InvalidCredentialsException();
        }
        String token = jwtUtils.generateJWT(loginUser, userDetails.getAuthorities().stream().map(Object::toString).toList());
        return new LoginResponse(token);
    }
    public User getByUsername(String username) throws UserNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return userRepository.save(user);
    }
    public User addUser(SignUpRequest signUpRequest) {
        User user = new User();
        user.setLastName(signUpRequest.getLastname());
        user.setFirstName(signUpRequest.getFirstname());
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setEmail(signUpRequest.getEmail());
        user.setRole(Role.user);
        return userRepository.save(user);
    }
}
