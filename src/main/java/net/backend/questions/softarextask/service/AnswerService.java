package net.backend.questions.softarextask.service;

import net.backend.questions.softarextask.dto.AnswerDto;
import net.backend.questions.softarextask.dto.AnswerUpdateDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface AnswerService {
    AnswerDto update(Integer id, AnswerUpdateDto answer);

    AnswerDto findByIdAndUserId(Integer id, Integer userId);

    List<AnswerDto> findAllByUserId(Integer userId);
}
