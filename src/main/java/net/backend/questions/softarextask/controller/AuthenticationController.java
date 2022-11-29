package net.backend.questions.softarextask.controller;

import lombok.RequiredArgsConstructor;
import net.backend.questions.softarextask.dto.UserCreateDto;
import net.backend.questions.softarextask.dto.UserDto;
import net.backend.questions.softarextask.dto.jwt.JwtRefreshRequestDto;
import net.backend.questions.softarextask.dto.jwt.JwtRequestDto;
import net.backend.questions.softarextask.dto.jwt.JwtResponseDto;
import net.backend.questions.softarextask.dto.jwt.JwtResponseWithoutRefreshDto;
import net.backend.questions.softarextask.service.AuthService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthService authService;

    @PostMapping("/login")
    public JwtResponseDto login(@Valid @RequestBody JwtRequestDto requestDto) {
        return authService.login(requestDto);
    }

    @PostMapping("/registration")
    public UserDto registration(@Valid @RequestBody UserCreateDto createDto) {
        return authService.registration(createDto);
    }

    @PostMapping("/token")
    public JwtResponseWithoutRefreshDto getNewAccessToken(@Valid @RequestBody JwtRefreshRequestDto request) {
        return authService.getAccessToken(request.getRefreshToken());
    }

    @PostMapping("/refresh")
    public JwtResponseDto getNewRefreshToken(@Valid @RequestBody JwtRefreshRequestDto request) {
        return authService.refresh(request.getRefreshToken());
    }
}
