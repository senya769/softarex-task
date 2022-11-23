package net.backend.questions.softarextask.controller;


import net.backend.questions.softarextask.dto.UserDto;
import net.backend.questions.softarextask.model.Answer;
import net.backend.questions.softarextask.model.Check;
import net.backend.questions.softarextask.model.Question;
import net.backend.questions.softarextask.model.User;
import net.backend.questions.softarextask.service.EmailService;
import net.backend.questions.softarextask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final EmailService emailService;

    @Autowired
    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping()
    public List<UserDto> getList() {
        return userService.findAllDto();
    }

    @PostMapping()
    public ResponseEntity<User> create(@RequestBody User user) {
        emailService.send(userService.create(user).getEmail(),"Account Test-Web","Your account was created!\nGood luck!");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{user_id}")
    public UserDto findById(@PathVariable("user_id") Integer id) {
        return userService.findByIdDto(id);
    }

    @PatchMapping("/{user_id}")
    public ResponseEntity<User> update(@PathVariable("user_id") Integer id, @RequestBody User user) {
        User userFromDB = userService.findById(id);
//        if(userFromDB.getPassword() == user.getPassword()) {
            userService.update(user, userFromDB);
            return new ResponseEntity<>(HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<User> delete(@PathVariable Integer user_id) {
        User user = userService.findById(user_id);
        emailService.send(user.getEmail(),"Account Test-Web","Your account was deleted.\nGood luck!");
        userService.delete(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{user_id}/check-password")
    public Check checkPassword(@PathVariable Integer user_id,@RequestBody User password){
        User byId = userService.findById(user_id);
        Check check = new Check();
        check.setAccept(Objects.equals(byId.getPassword(), password.getPassword()));
        return check;
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
