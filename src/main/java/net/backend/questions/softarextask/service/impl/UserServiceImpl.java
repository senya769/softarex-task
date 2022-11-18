package net.backend.questions.softarextask.service.impl;


import net.backend.questions.softarextask.model.User;
import net.backend.questions.softarextask.repository.UserRepository;
import net.backend.questions.softarextask.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

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
    public boolean create(User user) {
        User userFromDb = this.findByEmail(user.getEmail());
        if (userFromDb == null) {
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return true;
        }
        return false;

    }

    @Override
    public boolean update(User user) {
        User userFromDb = this.findByEmail(user.getEmail());
        if (userFromDb != null) {
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(User user) {
        User userFromDb = this.findByEmail(user.getEmail());
        if (userFromDb != null) {
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    @Override
    public User findById(int id) {
     return  userRepository.findById(id).orElseThrow();

    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()-> new NoSuchElementException("such email{"+email+"} was not found"));
    }

    @Override
    public List<User> findAll() {
//        return userRepository.findAll().stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    return userRepository.findAll();
    }
}
