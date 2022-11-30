package net.backend.questions.softarextask.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RequestConfirmPasswordDto {
    @NotBlank(message = "must not be empty")
    private String password;
}