package net.backend.questions.softarextask.controller;

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

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/v1/questions")
public class QuestController {
    private final QuestionService questionService;
    private final UserService userService;
    private final AnswerService answerService;

    @Autowired
    public QuestController(QuestionService questionService, UserService userService, AnswerService answerService) {
        this.questionService = questionService;
        this.userService = userService;
        this.answerService = answerService;
    }

    @GetMapping()
    public ResponseEntity<List<Question>> getAll(){
        List<Question> questions = questionService.findAll();
        return new ResponseEntity<>(questions,HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<Question> create(@RequestBody Question question){
        questionService.create(question);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    /*@GetMapping("/test")
    public ResponseEntity<User> test(){
        Answer answer = Answer.builder()
                .user(userService.findByEmail("2@"))
                .build();
        Question question = Question.builder()
                .question("How old you?")
                .typeAnswer(TypeAnswer.SIMPLE_STRING)
                .user(userService.findById(1))
                .answer(answer)
                .build();
        questionService.create(question);
        answerService.create(answer);
        return new ResponseEntity<>(HttpStatus.OK);
    }
*/
}
