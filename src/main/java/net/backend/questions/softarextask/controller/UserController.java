package net.backend.questions.softarextask.controller;


import net.backend.questions.softarextask.model.Answer;
import net.backend.questions.softarextask.model.Question;
import net.backend.questions.softarextask.model.User;
import net.backend.questions.softarextask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<User> getList() {
        return userService.findAll();
    }

    @PostMapping()
    public ResponseEntity<User> create(@RequestBody User user) {
        userService.create(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{user_id}")
    public User findById(@PathVariable("user_id") Integer id) {
        return userService.findById(id);
    }

    @PatchMapping("/{user_id}")
    public ResponseEntity<User> update(@PathVariable("user_id") Integer id, @RequestBody User user) {
        User userFromDB = userService.findById(id);
        userService.update(user, userFromDB);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<User> delete(@PathVariable Integer user_id) {
        //notify email after delete
        User byId = userService.findById(user_id);
        userService.delete(byId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{user_id}/questions")
    public Set<Question> getListQuestions(@PathVariable Integer user_id) {
        User byId = userService.findById(user_id);
        return byId.getQuestions();
    }

    @GetMapping("/{user_id}/answers")
    public Set<Answer> getListAnswers(@PathVariable Integer user_id) {
        User byId = userService.findById(user_id);
        return byId.getAnswers();
    }
}
