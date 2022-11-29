package net.backend.questions.softarextask.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.Map;

@Getter
@Setter
@Builder
public class NotValidResponse {
    private String message;
    @Singular
    private Map<Integer, String> details;
}
