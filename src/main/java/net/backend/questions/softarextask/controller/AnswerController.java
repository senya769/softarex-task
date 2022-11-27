package net.backend.questions.softarextask.controller;

import lombok.RequiredArgsConstructor;
import net.backend.questions.softarextask.dto.AnswerDto;
import net.backend.questions.softarextask.model.Answer;
import net.backend.questions.softarextask.service.AnswerService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(value = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/answers")
public class AnswerController {
    private final AnswerService answerService;

    @GetMapping("/{answerId}")
    public AnswerDto getById(@PathVariable Integer answerId, @PathVariable Integer userId) {
      return answerService.findByIdAndUserId(answerId,userId);
    }

    @PatchMapping("/{questId}")
    public AnswerDto toAnswer(@RequestBody Answer answer, @PathVariable Integer questId) {
        //web-socket
        return answerService.update(questId, answer);
    }
}
