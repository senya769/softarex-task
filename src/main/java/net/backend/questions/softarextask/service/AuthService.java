package net.backend.questions.softarextask.service;

import net.backend.questions.softarextask.dto.UserCreateDto;
import net.backend.questions.softarextask.dto.UserDto;
import net.backend.questions.softarextask.dto.jwt.JwtRequestDto;
import net.backend.questions.softarextask.dto.jwt.JwtResponseDto;
import net.backend.questions.softarextask.dto.jwt.JwtResponseWithoutRefreshDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AuthService {
    JwtResponseDto login(JwtRequestDto jwtRequestDto);

    UserDto registration(UserCreateDto user);

    JwtResponseWithoutRefreshDto getAccessToken(String refreshToken);

    JwtResponseDto refresh(String refreshToken);

}
