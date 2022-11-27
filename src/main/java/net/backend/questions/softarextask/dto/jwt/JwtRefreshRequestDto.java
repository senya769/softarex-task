package net.backend.questions.softarextask.dto.jwt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRefreshRequestDto {
    public String refreshToken;
}
