package net.backend.questions.softarextask.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.backend.questions.softarextask.dto.UserDto;

@Getter
@AllArgsConstructor
public class JwtResponse {

    private final String type = "Bearer";
    private String accessToken;
    private UserDto user;

}