package net.backend.questions.softarextask.controller;


import lombok.RequiredArgsConstructor;
import net.backend.questions.softarextask.domain.utils.CustomUserDetails;
import net.backend.questions.softarextask.domain.utils.JwtAuthentication;
import net.backend.questions.softarextask.dto.UserDto;
import net.backend.questions.softarextask.model.Answer;
import net.backend.questions.softarextask.model.Question;
import net.backend.questions.softarextask.model.User;
import net.backend.questions.softarextask.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @SendTo("/topic/users")
    @GetMapping()
    public List<UserDto> getList() {
        return userService.findAllDto();
    }

    @PostMapping()
    public UserDto create(@RequestBody User user) {
        return userService.create(user);
    }

    @GetMapping("/{userId}")
    public UserDto findById(@PathVariable("userId") Integer id) {
        return userService.findByIdDto(id);
    }

    @PatchMapping("/{userId}")
    public UserDto update(@PathVariable("userId") Integer id, @RequestBody User user) {
        return userService.update(id, user);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Integer userId) {
        userService.delete(userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{userId}/check-password")
    public void checkPassword(@PathVariable Integer userId, @RequestBody String password) {
        userService.isPasswordMatch(userId,password);
    }

    @SendTo("/topic/questions")
    @GetMapping("{userId}/questions")
    public Set<Question> getListQuestions(@PathVariable Integer userId) {
        User byId = userService.findById(userId);
        return byId.getQuestions();
    }

    @SendTo("/topic/answers")
    @GetMapping("/{userId}/answers")
    public Set<Answer> getListAnswers(@PathVariable Integer userId) {
        User byId = userService.findById(userId);
        return byId.getAnswers();
    }

    @PostMapping("/check/password")
    public Map<String,Boolean> checkPassword(@RequestBody RequestPasswordConfirmDto password) {
        JwtAuthentication authentication = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
        Map<String, Boolean> isMatch = Map.of("isMatch", userService.isPasswordMatch(authentication.getId(), password.getPassword()));
        return isMatch;
    }

}
