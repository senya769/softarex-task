package net.backend.questions.softarextask.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RequestConfirmPasswordDto {
    @NotEmpty(message = "must not be empty")
    private String password;
}
