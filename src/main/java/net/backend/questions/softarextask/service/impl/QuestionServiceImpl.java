package net.backend.questions.softarextask.service.impl;

import net.backend.questions.softarextask.model.Answer;
import net.backend.questions.softarextask.model.Question;
import net.backend.questions.softarextask.repository.QuestionRepository;
import net.backend.questions.softarextask.service.QuestionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;


    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Question create(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public void update(Question question, Question questionFromDb) {
        Answer answer = questionFromDb.getAnswer();
        answer.setAnswer("");
        questionFromDb.setAnswer(answer);
        questionFromDb.setQuestion(question.getQuestion());
        questionFromDb.setTypeAnswer(question.getTypeAnswer());
        questionRepository.save(questionFromDb);
    }

    @Override
    public boolean delete(Question question) {
        if (questionRepository.findById(question.getId()).isPresent()) {
            questionRepository.delete(question);
            return true;
        }
        return false;
    }

    @Override
    public Question findById(int id) {
        return questionRepository.findById(id).orElseThrow(() -> new NoSuchElementException("such Question ID{" + id + "} was not found"));
    }

    @Override
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public List<Question> findAllByUserId(Integer user_id) {
        return questionRepository.findAllByUserId(user_id).orElseThrow(() -> new NoSuchElementException("List Questions from User with ID(" + user_id + ") was not found"));
    }
}

