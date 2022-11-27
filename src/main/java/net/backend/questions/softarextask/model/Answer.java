package net.backend.questions.softarextask.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "answers")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @ToString.Exclude
    private User user;

    @OneToOne(mappedBy = "answer",cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Question question;
    private String answer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer1 = (Answer) o;
        return Objects.equals(user, answer1.user) && Objects.equals(question, answer1.question) && Objects.equals(answer, answer1.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, question, answer);
    }
}
