package net.backend.questions.softarextask.service;

import net.backend.questions.softarextask.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    void create(User user);
    void update(User user,User userFromBd);
    void delete(User user);
    User findById(int id);
    User findByEmail(String email);
    List<User> findAll();

}
