package net.backend.questions.softarextask.dto.jwt;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class JwtRefreshRequestDto {
    @NotBlank(message = "must not be empty")
    public String refreshToken;
}
