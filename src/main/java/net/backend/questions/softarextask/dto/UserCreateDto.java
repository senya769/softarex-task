package net.backend.questions.softarextask.dto;

import lombok.Data;
import net.backend.questions.softarextask.model.Roles;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserCreateDto {
    @NotBlank(message = "must not be empty")
    private String email;
    private String firstName;
    private String lastName;
    private String number;
    @NotEmpty(message = "must not be empty")
    private String password;
    private Set<Roles> roles = new HashSet<>();

    public void addRole(Roles role) {
        this.roles.add(role);
    }
}
