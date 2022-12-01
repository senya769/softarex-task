package net.backend.questions.softarextask.service;

import net.backend.questions.softarextask.dto.QuestionDto;
import net.backend.questions.softarextask.dto.QuestionRequestDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface QuestionService {
    QuestionDto create(Integer userId, String email, QuestionRequestDto question);

    QuestionDto update(Integer id, QuestionRequestDto question);

    void delete(Integer questId);

    QuestionDto findById(Integer id);

    QuestionDto findByIdAndUserId(Integer id, Integer userId);

    List<QuestionDto> findAllByUserId(Integer userId);
}
