package net.backend.questions.softarextask.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtResponse {

    private final String type = "Bearer";
    private String email;
    private String accessToken;

}