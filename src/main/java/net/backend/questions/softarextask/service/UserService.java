package net.backend.questions.softarextask.service;

import net.backend.questions.softarextask.dto.UserDto;
import net.backend.questions.softarextask.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User create(User user);

    void update(User user, User userFromBd);

    void delete(User user);

    User findById(int id);

    UserDto findByIdDto(int id);

    UserDto findByEmail(String email);

    List<UserDto> findAllDto();

    List<User> findAll();

}
