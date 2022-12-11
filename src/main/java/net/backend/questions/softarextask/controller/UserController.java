package net.backend.questions.softarextask.controller;


import lombok.RequiredArgsConstructor;
import net.backend.questions.softarextask.controller.mapping.UserURL;
import net.backend.questions.softarextask.dto.RequestConfirmPasswordDto;
import net.backend.questions.softarextask.dto.ResponseMatchPasswordDto;
import net.backend.questions.softarextask.dto.UserCreateDto;
import net.backend.questions.softarextask.dto.UserDto;
import net.backend.questions.softarextask.security.utils.JwtAuthentication;
import net.backend.questions.softarextask.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(UserURL.GET_ALL)
    public List<UserDto> getList() {
        return userService.findAllDto();
    }

    @GetMapping(UserURL.GET_BY_ID)
    public UserDto findById(@PathVariable("userId") Integer id) {
        return userService.findByIdDto(id);
    }

    @PatchMapping(UserURL.PATCH_BY_ID)
    public UserDto update(@PathVariable("userId") Integer id, @Valid @RequestBody UserCreateDto userCreateDto) {
        return userService.update(id, userCreateDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(UserURL.DELETE_BY_ID)
    public void delete(@PathVariable Integer userId) {
        userService.delete(userId);
    }

    @PostMapping(UserURL.POST_CHECK_PASSWORD)
    public ResponseMatchPasswordDto checkPassword(@Valid @RequestBody RequestConfirmPasswordDto passwordDto) {
        JwtAuthentication authentication = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
        Boolean passwordMatch = userService.isPasswordMatch(authentication.getId(), passwordDto.getPassword());
        return new ResponseMatchPasswordDto(passwordMatch);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(UserURL.POST_RESET_PASSWORD)
    public void resetPassword(@RequestParam @Email String email) {
        userService.resetPassword(email);
    }
}
