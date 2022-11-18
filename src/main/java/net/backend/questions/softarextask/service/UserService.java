package net.backend.questions.softarextask.service;

import net.backend.questions.softarextask.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    boolean create(User user);
    boolean update(User user);
    boolean delete(User user);
    User findById(int id);
    User findByEmail(String email);
    List<User> findAll();

}
