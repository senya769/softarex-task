package net.backend.questions.softarextask.service.impl;

import lombok.RequiredArgsConstructor;
import net.backend.questions.softarextask.dto.AnswerDto;
import net.backend.questions.softarextask.exception.AnswerException;
import net.backend.questions.softarextask.exception.QuestionException;
import net.backend.questions.softarextask.model.Answer;
import net.backend.questions.softarextask.repository.AnswerRepository;
import net.backend.questions.softarextask.service.AnswerService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;

    private final ModelMapper modelMapper;

    @Override
    public AnswerDto create(Answer answer) {
        Answer save = answerRepository.save(answer);
        return modelMapper.map(save, AnswerDto.class);
    }

    @Override
    public AnswerDto update(Integer questId, Answer answer) {
        Answer answerFromDb = answerRepository.findByQuestionId(questId)
                .orElseThrow(
                        () -> QuestionException.builder()
                                .message("This question with id was not found!")
                                .status(HttpStatus.NOT_FOUND)
                                .detail("Id: ", questId.toString())
                                .build()
                );
        answerFromDb.setAnswer(answer.getAnswer());
        Answer saveAnswer = answerRepository.save(answerFromDb);
        return modelMapper.map(saveAnswer, AnswerDto.class);
    }

    @Override
    public AnswerDto findByIdAndUserId(Integer answerId, Integer userId) {
        return answerRepository.findByIdAndUserId(answerId, userId)
                .map(answer -> modelMapper.map(answer, AnswerDto.class))
                .orElseThrow(
                        () -> AnswerException.builder()
                                .message("This user or question with id was not found!")
                                .status(HttpStatus.NOT_FOUND)
                                .detail("Id Answer: ", answerId.toString())
                                .detail("Id User: ", userId.toString())
                                .build()
                );
    }
}
