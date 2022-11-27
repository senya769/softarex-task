package net.backend.questions.softarextask.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import org.springframework.http.HttpStatus;

import java.util.Map;
@Getter
@Builder
public class ErrorResponse {
    private String message;

    @Singular
    private Map<String,String> details;
}
