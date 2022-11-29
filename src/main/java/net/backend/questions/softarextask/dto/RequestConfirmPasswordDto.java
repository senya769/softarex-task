package net.backend.questions.softarextask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class RequestConfirmPasswordDto {
    @NotBlank(message = "must not be empty")
    private String password;
}
