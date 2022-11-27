package net.backend.questions.softarextask.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String email;

    private String firstName;

    private String lastName;

    private String password;

    private String number;

    @ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles= new HashSet<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    @ToString.Exclude
    private Set<Question> questions = new HashSet<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    @ToString.Exclude
    private Set<Answer> answers = new HashSet<>();

    public void addRole(Roles role){
        this.roles.add(role);
    }
}
