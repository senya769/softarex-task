package net.backend.questions.softarextask.service;

import net.backend.questions.softarextask.model.Question;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface QuestionService {
    Question create(Question question);

    boolean update(Question question);

    boolean delete(Question question);

   Optional<Question> findById(int id);

    List<Question> findAll();


}
