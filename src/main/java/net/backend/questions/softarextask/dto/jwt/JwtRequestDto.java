package net.backend.questions.softarextask.dto.jwt;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtRequestDto {
    private String email;
    private String password;
}