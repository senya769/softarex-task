package net.backend.questions.softarextask.service;

import net.backend.questions.softarextask.dto.QuestionDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional
public interface QuestionService {
    QuestionDto create(Integer userId, String email, QuestionDto question);

    QuestionDto update(Integer id, QuestionDto question);

    void delete(Integer questId);

    QuestionDto findById(Integer id);

    QuestionDto findByIdAndUserId(Integer id, Integer userId);

    Set<QuestionDto> findAllByUserId(Integer userId);
}
