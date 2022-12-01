package net.backend.questions.softarextask.service.impl;

import lombok.RequiredArgsConstructor;
import net.backend.questions.softarextask.dto.QuestionDto;
import net.backend.questions.softarextask.dto.QuestionRequestDto;
import net.backend.questions.softarextask.exception.QuestionException;
import net.backend.questions.softarextask.model.Answer;
import net.backend.questions.softarextask.model.Question;
import net.backend.questions.softarextask.model.TypeAnswer;
import net.backend.questions.softarextask.model.User;
import net.backend.questions.softarextask.repository.QuestionRepository;
import net.backend.questions.softarextask.repository.UserRepository;
import net.backend.questions.softarextask.service.QuestionService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Override
    public QuestionDto create(Integer userId, String email, QuestionRequestDto question) {
        User userFrom = userRepository.findById(userId).orElseThrow(
                () -> UserServiceImpl.userNotFoundById(userId));
        User userFor = userRepository.findByEmail(email).orElseThrow(
                () -> UserServiceImpl.emailExists(email));
        Question readyToSave = modelMapper.map(question, Question.class);
        readyToSave.setTypeAnswer(TypeAnswer.fromString(question.getTypeAnswer()));
        readyToSave.setUser(userFrom);
        Answer answer = Answer.builder()
                .user(userFor)
                .build();
        readyToSave.setAnswer(answer);
        Question questionSave = questionRepository.save(readyToSave);
        return modelMapper.map(questionSave, QuestionDto.class);
    }

    @Override
    public QuestionDto update(Integer questionId, QuestionRequestDto question) {
        Question questionFromDb = questionRepository.findById(questionId)
                .orElseThrow(() -> questionNotFoundById(questionId));
        Answer answer = questionFromDb.getAnswer();
        answer.setAnswer(null);
        questionFromDb.setAnswer(answer);
        questionFromDb.setQuestion(question.getQuestion());
        questionFromDb.setTypeAnswer(TypeAnswer.fromString(question.getTypeAnswer()));
        Question save = questionRepository.save(questionFromDb);
        return modelMapper.map(save, QuestionDto.class);
    }

    @Override
    public void delete(Integer questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> questionNotFoundById(questionId));
        questionRepository.delete(question);
    }

    @Override
    public QuestionDto findById(Integer questionId) {
        return questionRepository.findById(questionId)
                .map(question -> modelMapper.map(question, QuestionDto.class))
                .orElseThrow(() -> questionNotFoundById(questionId));
    }

    @Override
    public QuestionDto findByIdAndUserId(Integer questionId, Integer userId) {
        return questionRepository.findByIdAndUserId(questionId, userId)
                .map(question -> modelMapper.map(question, QuestionDto.class))
                .orElseThrow(() -> questionNotFoundById(questionId));
    }

    @Override
    public List<QuestionDto> findAllByUserId(Integer userId) {
        return questionRepository.findAllByUserId(userId)
                .orElseThrow(() -> UserServiceImpl.userNotFoundById(userId))
                .stream()
                .map(questions -> modelMapper.map(questions, QuestionDto.class))
                .toList();
    }

    private QuestionException questionNotFoundById(Integer id) {
        return QuestionException.builder()
                .message("This question with id was not found!")
                .status(HttpStatus.NOT_FOUND)
                .detail("Id: ", id.toString())
                .build();
    }
}

