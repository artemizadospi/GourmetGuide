package com.pweb.gourmetguide.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SignUpDTO {
    private String lastname;
    private String firstname;
    private String username;
    private String password;
    private String email;
}
