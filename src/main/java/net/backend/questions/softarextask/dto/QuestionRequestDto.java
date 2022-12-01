package net.backend.questions.softarextask.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class QuestionRequestDto {
    @NotBlank(message = "must not be empty")
    private String question;
    @NotBlank(message = "must not be empty")
    private String typeAnswer;
}