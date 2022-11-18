package net.backend.questions.softarextask.controller;

import net.backend.questions.softarextask.model.Answer;
import net.backend.questions.softarextask.model.Question;
import net.backend.questions.softarextask.model.User;
import net.backend.questions.softarextask.service.AnswerService;
import net.backend.questions.softarextask.service.QuestionService;
import net.backend.questions.softarextask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/v2/user/{user_id}/questions")
public class QuestionController {
    private final QuestionService questionService;
    private final UserService userService;


    @Autowired
    public QuestionController(QuestionService questionService, UserService userService) {
        this.questionService = questionService;
        this.userService = userService;

    }

    @GetMapping()
    public List<Question> getAll(@PathVariable Integer user_id){
       return questionService.findAllByUserId(user_id);
    }

    @PostMapping("/{for_user_id}")
    public ResponseEntity<Question> create(@RequestBody Question question,
                                           @PathVariable Integer user_id,
                                           @PathVariable Integer for_user_id){
        User user = userService.findById(user_id);
        Answer answer = Answer.builder()
                .user(userService.findById(for_user_id))
                .build();
        question.setUser(user);
        question.setAnswer(answer);
        question.setTypeAnswer(question.getTypeAnswer());
        questionService.create(question);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/{quest_id}")
    public ResponseEntity<Question> findById(@PathVariable Integer user_id, @PathVariable Integer quest_id){
        Question byId = questionService.findById(quest_id);
        return new ResponseEntity<>(byId,HttpStatus.OK);
    }
    @DeleteMapping("/{quest_id}")
    public ResponseEntity<Question> delete(@PathVariable Integer quest_id){
        Question byId = questionService.findById(quest_id);
       if(questionService.delete(byId)){
        return new ResponseEntity<>(HttpStatus.OK);
       }
       return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
    @PatchMapping("/{quest_id}")
    public ResponseEntity<Question> update(@RequestBody Question question,@PathVariable Integer quest_id){
        Question questionFromDb = questionService.findById(quest_id);
        questionService.update(question,questionFromDb);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}