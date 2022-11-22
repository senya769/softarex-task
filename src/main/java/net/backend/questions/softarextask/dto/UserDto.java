package net.backend.questions.softarextask.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.backend.questions.softarextask.model.Answer;
import net.backend.questions.softarextask.model.Question;
import net.backend.questions.softarextask.model.Roles;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String number;
    @JsonManagedReference
    private Set<Answer> answers;
    @JsonManagedReference
    private Set<Question> questions;
    private Set<Roles>roles;


}
