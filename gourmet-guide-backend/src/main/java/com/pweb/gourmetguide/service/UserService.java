package com.pweb.gourmetguide.service;

import com.pweb.gourmetguide.dtos.LoginRequest;
import com.pweb.gourmetguide.dtos.LoginResponse;
import com.pweb.gourmetguide.dtos.SignUpDTO;
import com.pweb.gourmetguide.exception.InvalidCredentialsException;
import com.pweb.gourmetguide.exception.UserNotFoundException;
import com.pweb.gourmetguide.model.Role;
import com.pweb.gourmetguide.model.User;
import com.pweb.gourmetguide.repository.RoleRepository;
import com.pweb.gourmetguide.security.CustomUserDetails;
import com.pweb.gourmetguide.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.pweb.gourmetguide.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JWTUtils jwtUtils;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

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
        return user;
    }
    public SignUpDTO addUser(SignUpDTO signUpDTO) {
        User user = new User();
        Optional<Role> userRole = roleRepository.findById(2);
        user.setLastName(signUpDTO.getLastname());
        user.setFirstName(signUpDTO.getFirstname());
        user.setUsername(signUpDTO.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        user.setEmail(signUpDTO.getEmail());
        userRole.ifPresent(user::setRole);
        User savedUser = userRepository.save(user);
        return new SignUpDTO(savedUser.getLastName(), savedUser.getFirstName(),
                savedUser.getUsername(), savedUser.getPassword(), savedUser.getEmail());
    }
}
