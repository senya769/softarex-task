package net.backend.questions.softarextask.service;

import net.backend.questions.softarextask.dto.AnswerDto;
import net.backend.questions.softarextask.model.Answer;

public interface AnswerService {
    AnswerDto create(Answer answer);

    AnswerDto update(Integer id,Answer answer);

    AnswerDto findByIdAndUserId(Integer id, Integer userId);
}
