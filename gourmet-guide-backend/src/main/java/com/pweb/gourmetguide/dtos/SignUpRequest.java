package com.pweb.gourmetguide.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SignUpRequest {
    private String lastname;
    private String firstname;
    private String username;
    private String password;
    private String email;
}
