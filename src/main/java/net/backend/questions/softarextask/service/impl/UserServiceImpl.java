package net.backend.questions.softarextask.service.impl;


import lombok.RequiredArgsConstructor;
import net.backend.questions.softarextask.dto.UserDto;
import net.backend.questions.softarextask.exception.UserException;
import net.backend.questions.softarextask.model.Roles;
import net.backend.questions.softarextask.model.User;
import net.backend.questions.softarextask.repository.UserRepository;
import net.backend.questions.softarextask.service.EmailService;
import net.backend.questions.softarextask.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    private final ModelMapper modelMapper;

    @Override
    public UserDto create(User user) {
        Boolean hasEmail = userRepository.existsUserByEmail(user.getEmail());
        if (!hasEmail) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.addRole(Roles.USER_ROLE);
            User save = userRepository.save(user);
            emailService.send(save.getEmail(), "Account Test-Web", "Your account was created!\nGood luck!");
            return modelMapper.map(save, UserDto.class);
        } else {
            throw UserException.builder()
                    .message("This email already exists")
                    .status(HttpStatus.CONFLICT)
                    .detail("Email: ", user.getEmail())
                    .build();
        }
    }

    @Override
    public UserDto update(Integer userId, User user) {
        User userFromDb = userRepository.findById(userId).orElseThrow(
                () -> UserException.builder()
                        .message("This user with id was not found!")
                        .status(HttpStatus.NOT_FOUND)
                        .detail("Id: ", userId.toString())
                        .build()
        );
        Boolean hasEmail = userRepository.existsUserByEmail(user.getEmail());
        boolean isNewEmail = Objects.equals(user.getEmail(), userFromDb.getEmail());
        if (!hasEmail || isNewEmail) {
            userFromDb.setEmail(user.getEmail());
            userFromDb.setFirstName(user.getFirstName());
            userFromDb.setLastName(user.getLastName());
            userFromDb.setNumber(user.getNumber());
            userFromDb.setPassword(passwordEncoder.encode(user.getPassword()));
            User save = userRepository.save(userFromDb);
            return modelMapper.map(save, UserDto.class);
        } else {
            throw UserException.builder()
                    .message("This email already exists")
                    .status(HttpStatus.CONFLICT)
                    .detail("Email: ", user.getEmail())
                    .build();
        }
    }

    @Override
    public void delete(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> UserException.builder()
                                .message("This user with id was not found!")
                                .status(HttpStatus.NOT_FOUND)
                                .detail("Id: ", userId.toString())
                                .build()
                );
        userRepository.delete(user);
        emailService.send(user.getEmail(), "Account Test-Web", "Your account was deleted!\nGood luck!");
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(
                () -> UserException.builder()
                        .message("This user with id was not found!")
                        .status(HttpStatus.NOT_FOUND)
                        .detail("Id: ", id.toString())
                        .build()
        );
    }

    @Override
    public UserDto findByIdDto(Integer id) {
        return userRepository.findById(id)
                .map(user -> modelMapper.map(user, UserDto.class))
                .orElseThrow(
                        () -> UserException.builder()
                                .message("This user with id was not found!")
                                .status(HttpStatus.CONFLICT)
                                .detail("Id: ", id.toString())
                                .build()
                );
    }

    @Override
    public UserDto findByEmailDto(String email) {
        return userRepository.findByEmail(email)
                .map(user -> modelMapper.map(user, UserDto.class))
                .orElseThrow(
                        () -> UserException.builder()
                                .message("This email already exists")
                                .status(HttpStatus.CONFLICT)
                                .detail("Email: ", email)
                                .build()
                );
    }

    @Override
    public List<UserDto> findAllDto() {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }

    @Override
    public Boolean isPasswordMatch(Integer userId, String password) {
        User user = this.findById(userId);
        return passwordEncoder.matches(password, user.getPassword());
    }
}
