package net.backend.questions.softarextask.exception.handler;

import io.jsonwebtoken.security.SignatureException;
import net.backend.questions.softarextask.exception.*;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> notFoundException(ResourceNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorMessage> noSuchElement(NoSuchElementException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(JwtAuthException.class)
    public ResponseEntity<ErrorMessage> noAuth(JwtAuthException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorMessage> noSuchElement(SignatureException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> userHandler(UserException exception) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(ErrorResponse.builder()
                        .message(exception.getMessage())
                        .details(exception.getDetails()).build());
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> questionHandler(QuestionException exception) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(ErrorResponse.builder()
                        .message(exception.getMessage())
                        .details(exception.getDetails()).build());
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> answerHandler(AnswerException exception) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(ErrorResponse.builder()
                        .message(exception.getMessage())
                        .details(exception.getDetails()).build());
    }
}
