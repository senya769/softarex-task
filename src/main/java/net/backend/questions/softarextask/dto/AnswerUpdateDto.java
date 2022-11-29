package net.backend.questions.softarextask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class AnswerUpdateDto {
    @NotNull(message = "must not be empty")
    private String answer;
}
