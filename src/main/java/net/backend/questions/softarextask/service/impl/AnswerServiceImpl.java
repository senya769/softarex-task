package net.backend.questions.softarextask.service.impl;

import lombok.RequiredArgsConstructor;
import net.backend.questions.softarextask.dto.AnswerDto;
import net.backend.questions.softarextask.dto.AnswerUpdateDto;
import net.backend.questions.softarextask.exception.AnswerException;
import net.backend.questions.softarextask.model.Answer;
import net.backend.questions.softarextask.repository.AnswerRepository;
import net.backend.questions.softarextask.service.AnswerService;
import net.backend.questions.softarextask.service.QuestionService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public static int page = 1;

    public static int size = 3;

    @Override
    public AnswerDto update(Integer questId, AnswerUpdateDto answer) {
        Answer answerFromDb = answerRepository.findByQuestionId(questId)
                .orElseThrow(() -> notFoundAnswerByQuestionId(questId));
        answerFromDb.setAnswer(answer.getAnswer());
        Answer saveAnswer = answerRepository.save(answerFromDb);
        int userIdFromAnswer = saveAnswer.getUser().getId();
        int userIdFromQuestion = saveAnswer.getQuestion().getUser().getId();
        template.convertAndSend("/topic/questions/" + userIdFromAnswer,
                questionService.findAllByUserId(userIdFromAnswer, QuestionServiceImpl.page, QuestionServiceImpl.size));
        template.convertAndSend("/topic/answers/" + userIdFromQuestion,
                this.findAllByUserId(userIdFromQuestion, page, size));
        return modelMapper.map(saveAnswer, AnswerDto.class);
    }

    @Override
    public AnswerDto findByIdAndUserId(Integer answerId, Integer userId) {
        return answerRepository.findByIdAndUserId(answerId, userId)
                .map(answer -> modelMapper.map(answer, AnswerDto.class))
                .orElseThrow(() -> notFoundAnswerByIdAndUserId(answerId, userId));
    }

    @Override
    public List<AnswerDto> findAllByUserId(Integer userId, Integer page, Integer size) {
        AnswerServiceImpl.page = page;
        AnswerServiceImpl.size = size;
        return answerRepository.findAllByUserId(userId, PageRequest.of(page - 1, size, Sort.by("id")))
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
