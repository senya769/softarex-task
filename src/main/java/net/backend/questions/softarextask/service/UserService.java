package net.backend.questions.softarextask.service;

import net.backend.questions.softarextask.dto.UserCreateDto;
import net.backend.questions.softarextask.dto.UserDto;
import net.backend.questions.softarextask.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserService {
    UserDto create(UserCreateDto user);

    UserDto update(Integer userId, UserCreateDto user);

    void delete(Integer userId);

    User findById(Integer id);

    UserDto findByIdDto(Integer id);

    UserDto findByEmailDto(String email);

    List<UserDto> findAllDto();

    Boolean isPasswordMatch(Integer id, String password);
}
