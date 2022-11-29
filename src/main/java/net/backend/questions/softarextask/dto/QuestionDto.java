package net.backend.questions.softarextask.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import net.backend.questions.softarextask.model.TypeAnswer;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class QuestionDto {
    private Integer id;
    private UserDto user;
    @NotBlank(message = "must not be empty")
    private String question;
    @NotBlank(message = "must not be empty")
    private TypeAnswer typeAnswer;
    private AnswerDto answer;
}
