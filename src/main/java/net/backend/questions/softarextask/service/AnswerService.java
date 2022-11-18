package net.backend.questions.softarextask.service;

import net.backend.questions.softarextask.model.Answer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AnswerService {
    boolean create(Answer answer);

    boolean update(Answer answer);

    boolean delete(Answer answer);

    Answer findById(int id);

    List<Answer> findAll();

    List<Answer> findAllByUserId(Integer user_id);
}
