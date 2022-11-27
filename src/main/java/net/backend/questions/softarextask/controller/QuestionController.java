package net.backend.questions.softarextask.controller;

import net.backend.questions.softarextask.dto.QuestionDto;
import net.backend.questions.softarextask.model.Question;
import net.backend.questions.softarextask.model.User;
import net.backend.questions.softarextask.service.QuestionService;
import net.backend.questions.softarextask.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/users/{userId}/questions")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping()
    public QuestionDto create(@RequestBody Question question,
                              @RequestParam String email,
                              @PathVariable Integer userId) {
        //web-socket
        return questionService.create(userId, email, question);
    }

    @GetMapping("/{questId}")
    public QuestionDto findById(@PathVariable Integer questId, @PathVariable Integer userId) {
        return questionService.findByIdAndUserId(questId, userId);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping("/{questId}")
    public void delete(@PathVariable Integer questId) {
        //web-socket
        questionService.delete(questId);
    }

    @PatchMapping("/{questId}")
    public QuestionDto update(@RequestBody Question question, @PathVariable Integer questId) {
        //web-socket
        return questionService.update(questId, question);
    }
}
