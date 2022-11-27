package net.backend.questions.softarextask.service;

import net.backend.questions.softarextask.dto.UserDto;
import net.backend.questions.softarextask.model.User;

import java.util.List;

public interface UserService {
    UserDto create(User user);

    UserDto update(Integer userId, User userFromBd);

    void delete(Integer userId);

    User findById(Integer id);

    UserDto findByIdDto(Integer id);

    UserDto findByEmailDto(String email);

    List<UserDto> findAllDto();

    Boolean isPasswordMatch(Integer id, String password);
}
