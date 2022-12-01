package net.backend.questions.softarextask.controller;

import lombok.RequiredArgsConstructor;
import net.backend.questions.softarextask.controller.mapping.QuestionURL;
import net.backend.questions.softarextask.dto.QuestionDto;
import net.backend.questions.softarextask.dto.QuestionRequestDto;
import net.backend.questions.softarextask.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@CrossOrigin(value = "*")
@RestController
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping(QuestionURL.POST_CREATE)
    public QuestionDto create(@Valid @RequestBody QuestionRequestDto question,
                              @RequestParam @NotBlank String email,
                              @PathVariable Integer userId) {
        return questionService.create(userId, email, question);
    }

    @GetMapping(QuestionURL.GET_BY_ID)
    public QuestionDto findById(@PathVariable Integer questId, @PathVariable Integer userId) {
        return questionService.findByIdAndUserId(questId, userId);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping(QuestionURL.DELETE_BY_ID)
    public void delete(@PathVariable Integer questId) {
        questionService.delete(questId);
    }

    @PatchMapping(QuestionURL.PATCH_BY_ID)
    public QuestionDto update(@Valid @RequestBody QuestionRequestDto question, @PathVariable Integer questId) {
        return questionService.update(questId, question);
    }

    @SendTo("/topic/questions")
    @GetMapping(QuestionURL.GET_ALL_BY_USER)
    public List<QuestionDto> getListQuestions(@PathVariable Integer userId) {
        return questionService.findAllByUserId(userId);
    }
}
