package net.backend.questions.softarextask.controller;

import lombok.RequiredArgsConstructor;
import net.backend.questions.softarextask.controller.mapping.AnswerURL;
import net.backend.questions.softarextask.dto.AnswerDto;
import net.backend.questions.softarextask.dto.AnswerUpdateDto;
import net.backend.questions.softarextask.service.AnswerService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(value = "*")
@RestController
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;

    @GetMapping(AnswerURL.GET_BY_ID)
    public AnswerDto getById(@PathVariable Integer answerId, @PathVariable Integer userId) {
        return answerService.findByIdAndUserId(answerId, userId);
    }

    @PatchMapping(AnswerURL.PATCH_BY_QUESTION_ID)
    public AnswerDto toAnswer(@Valid @RequestBody AnswerUpdateDto answerUpdateDto, @PathVariable Integer questId) {
        return answerService.update(questId, answerUpdateDto);
    }

    @GetMapping(AnswerURL.GET_ALL_BY_USER)
    public List<AnswerDto> getListQuestions(@PathVariable Integer userId) {
        return answerService.findAllByUserId(userId);
    }
}
