package net.backend.questions.softarextask.service.impl;


import lombok.RequiredArgsConstructor;
import net.backend.questions.softarextask.dto.UserCreateDto;
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

import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    private final ModelMapper modelMapper;

    @Override
    public UserDto create(UserCreateDto user) {
        Boolean hasEmail = userRepository.existsUserByEmail(user.getEmail());
        if (hasEmail) {
            throw emailExists(user.getEmail());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.addRole(Roles.USER_ROLE);
        User userReadyToSave = modelMapper.map(user, User.class);
        User userSave = userRepository.save(userReadyToSave);
        emailService.send(userSave.getEmail(), "Account Test-Web", "Your account was created!\nGood luck!");
        return modelMapper.map(userSave, UserDto.class);
    }

    @Override
    public UserDto update(Integer userId, UserCreateDto user) {
        User userFromDb = userRepository.findById(userId)
                .orElseThrow(() -> userNotFoundById(userId));
        Boolean hasEmail = userRepository.existsUserByEmail(user.getEmail());
        boolean isNewEmail = !Objects.equals(user.getEmail(), userFromDb.getEmail());
        if (hasEmail && isNewEmail) {
            throw emailExists(user.getEmail());
        }
        userFromDb.setEmail(user.getEmail());
        userFromDb.setFirstName(user.getFirstName());
        userFromDb.setLastName(user.getLastName());
        userFromDb.setNumber(user.getNumber());
        userFromDb.setPassword(passwordEncoder.encode(user.getPassword()));
        User save = userRepository.save(userFromDb);
        return modelMapper.map(save, UserDto.class);
    }

    @Override
    public void delete(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> userNotFoundById(userId));
        userRepository.delete(user);
        emailService.send(user.getEmail(), "Account Test-Web", "Your account was deleted!\nGood luck!");
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> userNotFoundById(id));
    }

    @Override
    public UserDto findByIdDto(Integer id) {
        return userRepository.findById(id)
                .map(user -> modelMapper.map(user, UserDto.class))
                .orElseThrow(() -> userNotFoundById(id));
    }

    @Override
    public UserDto findByEmailDto(String email) {
        return userRepository.findByEmail(email)
                .map(user -> modelMapper.map(user, UserDto.class))
                .orElseThrow(() -> userNotFoundByEmail(email));
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

    @Override
    public void resetPassword(String email) {
        UserDto byEmailDto = findByEmailDto(email);
        User user = modelMapper.map(byEmailDto, User.class);
        String randomPassword = generateRandomPassword();
        user.setPassword(passwordEncoder.encode(randomPassword));
        userRepository.save(user);
        emailService.send(email, "Test-Web Reset Password", "Your new password is - " + randomPassword);
    }

    private String generateRandomPassword() {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int randomIndex = new Random().nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }
        return sb.toString();
    }

    public static UserException emailExists(String email) {
        return UserException.builder()
                .message("This email already exists")
                .status(HttpStatus.CONFLICT)
                .detail("Email: ", email)
                .build();
    }

    public static UserException userNotFoundById(Integer id) {
        return UserException.builder()
                .message("This user with id was not found!")
                .status(HttpStatus.CONFLICT)
                .detail("Id: ", id.toString())
                .build();
    }

    public static UserException userNotFoundByEmail(String email) {
        return UserException.builder()
                .message("This user with email was not found!")
                .status(HttpStatus.BAD_REQUEST)
                .detail("Email: ", email)
                .build();
    }
}
