package net.backend.questions.softarextask.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TypeAnswerException extends RuntimeException {
    private String message;
    private String detail;
}
