package net.backend.questions.softarextask.controller;

import net.backend.questions.softarextask.dto.AnswerDto;
import net.backend.questions.softarextask.model.Answer;
import net.backend.questions.softarextask.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/user/{user_id}/answers")
public class AnswerController {
    private final AnswerService answerService;
    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }
    @GetMapping()
    public ResponseEntity<List<AnswerDto>> getAll(@PathVariable("user_id") Integer id){
        List<AnswerDto> answers = answerService.findAllByUserId(id);
        return new ResponseEntity<>(answers,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Answer> create(@PathVariable("user_id") Integer id,@RequestBody Answer answer){
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
