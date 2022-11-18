package net.backend.questions.softarextask.service;

import net.backend.questions.softarextask.model.Question;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuestionService {
    Question create(Question question);

    void update(Question question, Question questionFromDb);

    boolean delete(Question question);

    Question findById(int id);

    List<Question> findAll();

    List<Question> findAllByUserId(Integer user_id);

}
