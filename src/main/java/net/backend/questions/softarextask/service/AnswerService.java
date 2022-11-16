package net.backend.questions.softarextask.service;

import net.backend.questions.softarextask.model.Answer;
import net.backend.questions.softarextask.model.Question;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AnswerService {
    boolean create (Answer answer);
    boolean update(Answer answer);
    boolean delete(Answer answer);
    Optional<Answer> findById(int id);
    List<Answer> findAll();

}
