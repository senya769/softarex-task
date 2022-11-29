package net.backend.questions.softarextask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseMatchPasswordDto {
    private Boolean isMatch;
}
