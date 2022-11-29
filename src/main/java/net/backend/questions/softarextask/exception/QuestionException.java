package net.backend.questions.softarextask.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
@Builder
public class QuestionException extends RuntimeException {
    private String message;

    private HttpStatus status;

    @Singular
    private Map<String, String> details;

    public QuestionException(String message, HttpStatus status, Map<String, String> details) {
        this.message = message;
        this.status = status;
        this.details = details;
    }
}
