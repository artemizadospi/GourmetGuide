package com.pweb.gourmetguide.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.pweb.gourmetguide.model.User;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JWTUtils {
    private final Algorithm algorithm = Algorithm.HMAC256("encryptionSecret");

    public String generateJWT(User user, List<String> roles) {
        Map<String, String> payloadClaims = new HashMap<>();
        payloadClaims.put("firstName", user.getFirstName());
        payloadClaims.put("lastName", user.getLastName());
        payloadClaims.put("email", user.getEmail());
        payloadClaims.put("username", user.getUsername());
        String role = roles.get(0);
        if (role.startsWith("ROLE_"))
            role = role.substring("ROLE_".length());
        payloadClaims.put("role", role);
        payloadClaims.put("id", user.getId() + "");
        return JWT.create()
                .withPayload(payloadClaims)
                .withIssuer("gourmetguide")
                .withIssuedAt(new Date())
                .sign(algorithm);
    }
}
