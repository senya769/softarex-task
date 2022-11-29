package net.backend.questions.softarextask.service.impl;

import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.backend.questions.softarextask.dto.UserCreateDto;
import net.backend.questions.softarextask.dto.UserDto;
import net.backend.questions.softarextask.dto.jwt.JwtRequestDto;
import net.backend.questions.softarextask.dto.jwt.JwtResponseDto;
import net.backend.questions.softarextask.dto.jwt.JwtResponseWithoutRefreshDto;
import net.backend.questions.softarextask.exception.JwtAuthException;
import net.backend.questions.softarextask.exception.UserException;
import net.backend.questions.softarextask.security.JwtTokenProvider;
import net.backend.questions.softarextask.service.AuthService;
import net.backend.questions.softarextask.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final Map<String, String> refreshStorage = new HashMap<>();

    @Override
    public JwtResponseDto login(JwtRequestDto requestDto) {
        String email = requestDto.getEmail();
        UserDto user = userService.findByEmailDto(email);
        if (!userService.isPasswordMatch(user.getId(), requestDto.getPassword())) {
            throw UserException.builder()
                    .message("This combination of email and password was not found")
                    .detail("email:", email)
                    .detail("password", requestDto.getPassword())
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, requestDto.getPassword()));
        String token = jwtTokenProvider.generateAccessToken(user);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user);
        refreshStorage.put(user.getEmail(), refreshToken);
        return new JwtResponseDto(user.getId(), email, token, refreshToken);
    }

    @Override
    public UserDto registration(UserCreateDto userCreateDto) {
        return userService.create(userCreateDto);
    }

    @Override
    public JwtResponseWithoutRefreshDto getAccessToken(@NonNull String refreshToken) {
        if (jwtTokenProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtTokenProvider.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(email);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final UserDto user = userService.findByEmailDto(email);
                final String accessToken = jwtTokenProvider.generateAccessToken(user);
                return new JwtResponseWithoutRefreshDto(user.getId(), user.getEmail(), accessToken);
            }
        }
        throw new JwtAuthException("JWT token is expired or invalid\"");
    }

    @Override
    public JwtResponseDto refresh(@NonNull String refreshToken) {
        if (jwtTokenProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtTokenProvider.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(email);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final UserDto user = userService.findByEmailDto(email);
                final String accessToken = jwtTokenProvider.generateAccessToken(user);
                final String newRefreshToken = jwtTokenProvider.generateRefreshToken(user);
                refreshStorage.put(user.getEmail(), newRefreshToken);
                return new JwtResponseDto(user.getId(), user.getEmail(), accessToken, newRefreshToken);
            }
        }
        throw new JwtAuthException("JWT token is expired or invalid\"");
    }
}
