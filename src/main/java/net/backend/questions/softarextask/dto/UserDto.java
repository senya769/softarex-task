package net.backend.questions.softarextask.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import net.backend.questions.softarextask.model.Roles;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class UserDto {
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String number;
    private Set<AnswerDto> answers;
    private Set<QuestionDto> questions;
    @Singular
    private Set<Roles>roles;
}
