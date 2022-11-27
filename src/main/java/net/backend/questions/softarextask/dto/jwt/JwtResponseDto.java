package net.backend.questions.softarextask.dto.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class JwtResponseDto {
    private Integer id;
    private String email;
    private String accessToken;
    private String refreshToken;
}