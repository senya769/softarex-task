package net.backend.questions.softarextask.controller;

import net.backend.questions.softarextask.dto.QuestionDto;
import net.backend.questions.softarextask.model.Answer;
import net.backend.questions.softarextask.model.Question;
import net.backend.questions.softarextask.model.TypeAnswer;
import net.backend.questions.softarextask.model.User;
import net.backend.questions.softarextask.service.AnswerService;
import net.backend.questions.softarextask.service.QuestionService;
import net.backend.questions.softarextask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/v2/user/{user_id}/questions")
public class QuestionController {
    private final QuestionService questionService;
    private final UserService userService;
    private final AnswerService answerService;

    @Autowired
    public QuestionController(QuestionService questionService, UserService userService, AnswerService answerService) {
        this.questionService = questionService;
        this.userService = userService;
        this.answerService = answerService;
    }

    @GetMapping()
    public List<QuestionDto> getAll(@PathVariable Integer user_id){
       return questionService.findAllByUserId(user_id);
    }
    @PostMapping()
    public ResponseEntity<Question> create(@RequestBody Question question){
        questionService.create(question);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/{quest_id}")
    public ResponseEntity<Question> findById(@PathVariable Integer user_id, @PathVariable Integer quest_id){
        Question byId = questionService.findById(quest_id).orElseThrow(() -> new NoSuchElementException("such ID("+quest_id+") does not exist"));
        return new ResponseEntity<>(byId,HttpStatus.OK);
    }

}
