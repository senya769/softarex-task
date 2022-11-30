package net.backend.questions.softarextask.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AnswerUpdateDto {
    @NotNull(message = "must not be empty")
    private String answer;
}
