package net.backend.questions.softarextask.service.impl;


import net.backend.questions.softarextask.exception.ResourceNotFoundException;
import net.backend.questions.softarextask.model.User;
import net.backend.questions.softarextask.repository.UserRepository;
import net.backend.questions.softarextask.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void create(User user) {
        User byEmail = userRepository.findByEmail(user.getEmail()).orElse(null);
        if(byEmail == null){
            userRepository.save(user);
        }
    }

    @Override
    public void update(User user,User userFromDb) {
        userFromDb.setEmail(user.getEmail());
        userFromDb.setFirstName(user.getFirstName());
        userFromDb.setLastName(user.getLastName());
        userFromDb.setNumber(user.getNumber());
        userRepository.save(userFromDb);
    }

    @Override
    public void delete(User user) {
      if (userRepository.findByEmail(user.getEmail()).isPresent()){
          userRepository.delete(user);
      }else {
          userRepository.findByEmail(user.getEmail())
                  .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
      }
    }

    @Override
    public User findById(int id) {
     return  userRepository.findById(id)
             .orElseThrow(() ->new NoSuchElementException("such User with ID{"+id+"} was not found") );

    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new NoSuchElementException("such User with email{"+email+"} was not found"));
    }

    @Override
    public List<User> findAll() {
//        return userRepository.findAll().stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    return Optional.of(userRepository.findAll())
            .orElseThrow(() -> new ResourceNotFoundException("User resource is not found"));
    }
}
