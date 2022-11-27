package net.backend.questions.softarextask.exception;

import io.jsonwebtoken.JwtException;

public class JwtAuthException extends JwtException {
    public JwtAuthException(String message) {
        super(message);
    }
}
