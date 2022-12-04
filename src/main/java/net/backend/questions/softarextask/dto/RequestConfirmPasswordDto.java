package net.backend.questions.softarextask.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class RequestConfirmPasswordDto {
    @NotEmpty(message = "must not be empty")
    @Size(min = 5, max = 20)
    private String password;
}
