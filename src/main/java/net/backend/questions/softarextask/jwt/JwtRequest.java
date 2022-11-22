package net.backend.questions.softarextask.jwt;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtRequest {

    private String email;
    private String password;

}