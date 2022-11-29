package net.backend.questions.softarextask.exception.handler;

import io.jsonwebtoken.security.SignatureException;
import net.backend.questions.softarextask.exception.*;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> noSuchElement(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        Map<String, String> map = new HashMap<>();
        fieldErrors.forEach(fieldError -> map.put(fieldError.getField(), fieldError.getDefaultMessage()));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .message("Value is not valid")
                        .details(map)
                        .build());
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
                        .details(exception.getDetails())
                        .build());
    }

    @ExceptionHandler(QuestionException.class)
    public ResponseEntity<ErrorResponse> questionHandler(QuestionException exception) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(ErrorResponse.builder()
                        .message(exception.getMessage())
                        .details(exception.getDetails())
                        .build());
    }

    @ExceptionHandler(AnswerException.class)
    public ResponseEntity<ErrorResponse> answerHandler(AnswerException exception) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(ErrorResponse.builder()
                        .message(exception.getMessage())
                        .details(exception.getDetails())
                        .build());
    }
}
