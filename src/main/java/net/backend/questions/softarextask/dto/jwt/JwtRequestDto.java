package net.backend.questions.softarextask.dto.jwt;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class JwtRequestDto {
    @NotBlank(message = "must not be empty")
    private String email;
    @NotBlank(message = "must not be empty")
    private String password;
}