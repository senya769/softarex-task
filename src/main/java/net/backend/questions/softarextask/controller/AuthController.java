package net.backend.questions.softarextask.controller;

import net.backend.questions.softarextask.dto.UserDto;
import net.backend.questions.softarextask.jwt.JwtRequest;
import net.backend.questions.softarextask.jwt.JwtResponse;
import net.backend.questions.softarextask.jwt.JwtTokenProvider;
import net.backend.questions.softarextask.model.Roles;
import net.backend.questions.softarextask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("http://localhost:3000")
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
        String email = requestDto.getEmail();
        UserDto user = userService.findByEmail(email);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, requestDto.getPassword()));
        List<Roles> roles = user.getRoles().stream().toList();
        String token = jwtTokenProvider.createToken(user, roles);
        return new ResponseEntity<>(new JwtResponse(token, user), HttpStatus.OK);
    }
}
