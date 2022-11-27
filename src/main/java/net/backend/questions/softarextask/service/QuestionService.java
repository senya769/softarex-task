package net.backend.questions.softarextask.service;

import net.backend.questions.softarextask.dto.QuestionDto;
import net.backend.questions.softarextask.model.Question;

public interface QuestionService {
    QuestionDto create(Integer userId,String email,Question question);

    QuestionDto update(Integer id,Question question);

    void delete(Integer questId);

    QuestionDto findById(Integer id);

    QuestionDto findByIdAndUserId(Integer id, Integer userId);
}
