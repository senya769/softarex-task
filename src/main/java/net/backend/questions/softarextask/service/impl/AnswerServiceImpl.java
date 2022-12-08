package net.backend.questions.softarextask.service.impl;

import lombok.RequiredArgsConstructor;
import net.backend.questions.softarextask.dto.AnswerDto;
import net.backend.questions.softarextask.dto.AnswerUpdateDto;
import net.backend.questions.softarextask.exception.AnswerException;
import net.backend.questions.softarextask.model.Answer;
import net.backend.questions.softarextask.model.User;
import net.backend.questions.softarextask.repository.AnswerRepository;
import net.backend.questions.softarextask.service.AnswerService;
import net.backend.questions.softarextask.service.QuestionService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;

    private final QuestionService questionService;

    private final SimpMessagingTemplate template;

    private final ModelMapper modelMapper;

    @Override
    public AnswerDto update(Integer questId, AnswerUpdateDto answer) {
        Answer answerFromDb = answerRepository.findByQuestionId(questId)
                .orElseThrow(() -> notFoundAnswerByQuestionId(questId));
        answerFromDb.setAnswer(answer.getAnswer());
        Answer saveAnswer = answerRepository.save(answerFromDb);
        User userAnswer = saveAnswer.getUser();
        User userQuestion = saveAnswer.getQuestion().getUser();
        template.convertAndSend("/topic/questions",
                questionService.findAllByUserId(userQuestion.getId()));
        template.convertAndSend("/topic/answers",
                this.findAllByUserId(userAnswer.getId()));
        return modelMapper.map(saveAnswer, AnswerDto.class);
    }

    @Override
    public AnswerDto findByIdAndUserId(Integer answerId, Integer userId) {
        return answerRepository.findByIdAndUserId(answerId, userId)
                .map(answer -> modelMapper.map(answer, AnswerDto.class))
                .orElseThrow(() -> notFoundAnswerByIdAndUserId(answerId, userId));
    }

    @Override
    public List<AnswerDto> findAllByUserId(Integer userId) {
        return answerRepository.findAllByUserId(userId)
                .orElseThrow(() -> UserServiceImpl.userNotFoundById(userId))
                .stream()
                .map(answer -> modelMapper.map(answer, AnswerDto.class))
                .toList();
    }

    private static AnswerException notFoundAnswerByQuestionId(Integer id) {
        return AnswerException.builder()
                .message("This answer with question id was not found!")
                .status(HttpStatus.NOT_FOUND)
                .detail("Id: ", id.toString())
                .build();
    }

    private static AnswerException notFoundAnswerByIdAndUserId(Integer id, Integer userId) {
        return AnswerException.builder()
                .message("This user or question with id was not found!")
                .status(HttpStatus.NOT_FOUND)
                .detail("Id Answer: ", id.toString())
                .detail("Id User: ", userId.toString())
                .build();
    }
}
