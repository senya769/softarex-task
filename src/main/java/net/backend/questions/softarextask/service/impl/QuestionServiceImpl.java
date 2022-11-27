package net.backend.questions.softarextask.service.impl;

import lombok.RequiredArgsConstructor;
import net.backend.questions.softarextask.dto.QuestionDto;
import net.backend.questions.softarextask.exception.QuestionException;
import net.backend.questions.softarextask.exception.UserException;
import net.backend.questions.softarextask.model.Answer;
import net.backend.questions.softarextask.model.Question;
import net.backend.questions.softarextask.model.User;
import net.backend.questions.softarextask.repository.QuestionRepository;
import net.backend.questions.softarextask.repository.UserRepository;
import net.backend.questions.softarextask.service.QuestionService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Override
    public QuestionDto create(Integer userId, String email, Question question) {
        User userFrom = userRepository.findById(userId).orElseThrow(
                () -> UserException.builder()
                        .message("This question with id was not found!")
                        .status(HttpStatus.NOT_FOUND)
                        .detail("Id: ", userId.toString())
                        .build()
        );
        User userFor = userRepository.findByEmail(email).orElseThrow(
                () -> UserException.builder()
                        .message("This email already exists")
                        .status(HttpStatus.CONFLICT)
                        .detail("Email: ", email)
                        .build()
        );
        question.setUser(userFrom);
        Answer answer = Answer.builder()
                .user(userFor)
                .build();
        question.setAnswer(answer);
        Question save = questionRepository.save(question);
        return modelMapper.map(save, QuestionDto.class);
    }

    @Override
    public QuestionDto update(Integer questionId, Question question) {
        Question questionFromDb = questionRepository.findById(questionId)
                .orElseThrow(
                        () -> QuestionException.builder()
                                .message("This question with id was not found!")
                                .status(HttpStatus.NOT_FOUND)
                                .detail("Id: ", questionId.toString())
                                .build()
                );
        Answer answer = questionFromDb.getAnswer();
        answer.setAnswer(null);
        questionFromDb.setAnswer(answer);
        questionFromDb.setQuestion(question.getQuestion());
        questionFromDb.setTypeAnswer(question.getTypeAnswer());
        Question save = questionRepository.save(questionFromDb);
        return modelMapper.map(save, QuestionDto.class);
    }

    @Override
    public void delete(Integer questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(
                        () -> QuestionException.builder()
                                .message("This question with id was not found!")
                                .status(HttpStatus.NOT_FOUND)
                                .detail("Id: ", questionId.toString())
                                .build()
                );
        questionRepository.delete(question);
    }

    @Override
    public QuestionDto findById(Integer questionId) {
        return questionRepository.findById(questionId)
                .map(question -> modelMapper.map(question, QuestionDto.class))
                .orElseThrow(
                        () -> QuestionException.builder()
                                .message("This question with id was not found!")
                                .status(HttpStatus.NOT_FOUND)
                                .detail("Id: ", questionId.toString())
                                .build()
                );
    }

    @Override
    public QuestionDto findByIdAndUserId(Integer questionId, Integer userId) {
        return questionRepository.findByIdAndUserId(questionId, userId)
                .map(question -> modelMapper.map(question, QuestionDto.class))
                .orElseThrow(
                        () -> QuestionException.builder()
                                .message("This user or question with id was not found!")
                                .status(HttpStatus.NOT_FOUND)
                                .detail("Id Question: ", questionId.toString())
                                .detail("Id User: ", userId.toString())
                                .build()
                );
    }
}

