package net.backend.questions.softarextask.service;

import net.backend.questions.softarextask.dto.UserDto;
import net.backend.questions.softarextask.dto.jwt.JwtRequestDto;
import net.backend.questions.softarextask.dto.jwt.JwtResponseDto;
import net.backend.questions.softarextask.model.User;

public interface AuthService {
    JwtResponseDto login(JwtRequestDto jwtRequestDto);

    UserDto registration(User user);

    JwtResponseDto getAccessToken(String refreshToken);

    JwtResponseDto refresh(String refreshToken);

}
