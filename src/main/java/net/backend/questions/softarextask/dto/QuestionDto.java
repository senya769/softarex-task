package net.backend.questions.softarextask.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import net.backend.questions.softarextask.model.TypeAnswer;
@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class QuestionDto {
    private Integer id;
    private UserDto user;
    private String question;
    private TypeAnswer typeAnswer;
    private AnswerDto answer;
}
