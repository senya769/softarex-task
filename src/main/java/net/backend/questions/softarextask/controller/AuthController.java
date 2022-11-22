package net.backend.questions.softarextask.controller;

import net.backend.questions.softarextask.dto.UserDto;
import net.backend.questions.softarextask.jwt.JwtRequest;
import net.backend.questions.softarextask.jwt.JwtResponse;
import net.backend.questions.softarextask.jwt.JwtTokenProvider;
import net.backend.questions.softarextask.model.User;
import net.backend.questions.softarextask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@RestController
public class AuthController {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest requestDto) {
        try {
            String email = requestDto.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, requestDto.getPassword()));
            UserDto user = userService.findByEmail(email);

            if (user == null) {
                throw new UsernameNotFoundException("User with email: " + email + " not found");
            }

            String token = jwtTokenProvider.createToken(user, user.getRoles().stream().toList());
            JwtResponse jwtResponse = new JwtResponse(email,token);
            return ResponseEntity.ok(jwtResponse);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }
}
