package net.backend.questions.softarextask.controller;

import net.backend.questions.softarextask.model.Answer;
import net.backend.questions.softarextask.model.Question;
import net.backend.questions.softarextask.service.AnswerService;
import net.backend.questions.softarextask.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/users/{user_id}/answers")
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;
    @Autowired
    public AnswerController(AnswerService answerService, QuestionService questionService) {
        this.answerService = answerService;
        this.questionService = questionService;
    }

    @GetMapping("/{ans_id}")
    public ResponseEntity<Answer> getById(@PathVariable("user_id") Integer id, @PathVariable Integer ans_id){
        Answer answer = answerService.findById(ans_id);
        return new ResponseEntity<>(answer,HttpStatus.OK);
    }

    @PatchMapping("/{quest_id}")
    public ResponseEntity<Answer> toAnswer(@RequestBody Answer answer,
                                         @PathVariable Integer quest_id){
        Question byId = questionService.findById(quest_id);
        Answer answerFromDb = byId.getAnswer();
        answerFromDb.setAnswer(answer.getAnswer());
        answerService.update(answerFromDb);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{ans_id}")
    public ResponseEntity<Answer> delete(@PathVariable Integer ans_id){
        Answer answer = answerService.findById(ans_id);
        answer.setAnswer(null);
        answerService.update(answer);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
