package net.backend.questions.softarextask.service.impl;

import lombok.RequiredArgsConstructor;
import net.backend.questions.softarextask.dto.AnswerDto;
import net.backend.questions.softarextask.dto.QuestionDto;
import net.backend.questions.softarextask.dto.QuestionRequestDto;
import net.backend.questions.softarextask.exception.QuestionException;
import net.backend.questions.softarextask.model.Answer;
import net.backend.questions.softarextask.model.Question;
import net.backend.questions.softarextask.model.TypeAnswer;
import net.backend.questions.softarextask.model.User;
import net.backend.questions.softarextask.repository.AnswerRepository;
import net.backend.questions.softarextask.repository.QuestionRepository;
import net.backend.questions.softarextask.repository.UserRepository;
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
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;

    private final UserRepository userRepository;

    private final AnswerRepository answerRepository;

    private final SimpMessagingTemplate template;

    private final ModelMapper modelMapper;

    public static int page = 1;

    public static int size = 3;

    @Override
    public QuestionDto create(Integer userId, String email, QuestionRequestDto question) {
        User userFrom = userRepository.findById(userId).orElseThrow(
                () -> UserServiceImpl.userNotFoundById(userId));
        User userFor = userRepository.findByEmail(email).orElseThrow(
                () -> UserServiceImpl.userNotFoundByEmail(email));
        Question readyToSave = modelMapper.map(question, Question.class);
        readyToSave.setTypeAnswer(TypeAnswer.fromString(question.getTypeAnswer()));
        readyToSave.setUser(userFrom);
        Answer answer = Answer.builder()
                .user(userFor)
                .question(readyToSave)
                .build();
        readyToSave.setAnswer(answer);
        Question questionSave = questionRepository.save(readyToSave);
        template.convertAndSend("/topic/questions/" + userId,
                this.findAllByUserId(userId, page, size));
        template.convertAndSend("/topic/answers/" + userFor.getId(),
                getAllAnswersByUserIdForTopic(userFor.getId()));
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
        int userIdFromQuestion = save.getUser().getId();
        int userIdFromAnswer = answer.getUser().getId();
        template.convertAndSend("/topic/questions/" + userIdFromQuestion,
                this.findAllByUserId(userIdFromQuestion, page, size));
        template.convertAndSend("/topic/answers/" + userIdFromAnswer,
                getAllAnswersByUserIdForTopic(userIdFromAnswer));
        return modelMapper.map(save, QuestionDto.class);
    }

    @Override
    public void delete(Integer questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> questionNotFoundById(questionId));
        questionRepository.delete(question);
        int userIdFromQuestion = question.getUser().getId();
        int userIdFromAnswer = question.getAnswer().getUser().getId();
        template.convertAndSend("/topic/questions/" + userIdFromQuestion,
                this.findAllByUserId(userIdFromQuestion, page, size));
        template.convertAndSend("/topic/answers/" + userIdFromAnswer,
                getAllAnswersByUserIdForTopic(userIdFromAnswer));
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
    public List<QuestionDto> findAllByUserId(Integer userId, Integer page, Integer size) {
        QuestionServiceImpl.page = page;
        QuestionServiceImpl.size = size;
        return questionRepository.findAllByUserId(userId, PageRequest.of(page - 1, size, Sort.by("id")))
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

    private List<AnswerDto> getAllAnswersByUserIdForTopic(Integer userId) {
        return answerRepository.findAllByUserId(userId,
                        PageRequest.of(AnswerServiceImpl.page - 1, AnswerServiceImpl.size, Sort.by("id")))
                .stream()
                .map(ans -> modelMapper.map(ans, AnswerDto.class))
                .toList();
    }
}

