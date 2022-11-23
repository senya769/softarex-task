package net.backend.questions.softarextask.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import net.backend.questions.softarextask.model.Answer;
import net.backend.questions.softarextask.model.TypeAnswer;
import net.backend.questions.softarextask.model.User;

import javax.persistence.*;
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
