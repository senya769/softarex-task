package net.backend.questions.softarextask.service.impl;


import net.backend.questions.softarextask.dto.UserDto;
import net.backend.questions.softarextask.exception.ResourceNotFoundException;
import net.backend.questions.softarextask.model.Roles;
import net.backend.questions.softarextask.model.User;
import net.backend.questions.softarextask.repository.UserRepository;
import net.backend.questions.softarextask.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public User create(User user) {
        User byEmail = userRepository.findByEmail(user.getEmail()).orElse(null);
        if (byEmail == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Set<Roles> roles = user.getRoles();
            roles.add(Roles.USER_ROLE);
            user.setRoles(roles);
           return userRepository.save(user);
        }
        return null;
    }

    @Override
    public void update(User user, User userFromDb) {
        userFromDb.setEmail(user.getEmail());
        userFromDb.setFirstName(user.getFirstName());
        userFromDb.setLastName(user.getLastName());
        userFromDb.setNumber(user.getNumber());
        userFromDb.setPassword(user.getPassword());
        userRepository.save(userFromDb);
    }

    @Override
    public void delete(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            userRepository.delete(user);
        } else {
            userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        }
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("such User with ID{" + id + "} was not found"));
    }

    @Override
    public UserDto findByIdDto(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("such User with ID{" + id + "} was not found"));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException("such User with email{" + email + "} was not found"));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> findAllDto() {
        return Optional.of(userRepository.findAll()).orElseThrow(() -> new ResourceNotFoundException("User resource is not found")).stream().map(user -> modelMapper.map(user, UserDto.class)).toList();
    }

    @Override
    public List<User> findAll() {
        return Optional.of(userRepository.findAll()).orElseThrow(() -> new ResourceNotFoundException("User resource is not found"));
    }
}
