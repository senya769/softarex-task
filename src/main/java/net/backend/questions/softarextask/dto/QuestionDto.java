package net.backend.questions.softarextask.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.backend.questions.softarextask.model.TypeAnswer;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class QuestionDto {
    private Integer id;
    @JsonProperty("user")
    private UserDto user;
    private String question;
    private TypeAnswer typeAnswer;
    @JsonProperty("answer")
    private AnswerDto answer;

}
