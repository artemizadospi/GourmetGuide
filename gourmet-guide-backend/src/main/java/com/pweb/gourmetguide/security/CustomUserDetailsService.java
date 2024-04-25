package com.pweb.gourmetguide.security;

import com.pweb.gourmetguide.exception.InvalidCredentialsException;
import com.pweb.gourmetguide.exception.InvalidUsernameException;
import com.pweb.gourmetguide.model.User;
import com.pweb.gourmetguide.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws InvalidUsernameException {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new InvalidUsernameException();
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().getValue()));
        String password = user.getPassword();

        return new CustomUserDetails(username, authorities, password);
    }
}
