package net.backend.questions.softarextask.service.impl;

import net.backend.questions.softarextask.model.Answer;
import net.backend.questions.softarextask.repository.AnswerRepository;
import net.backend.questions.softarextask.service.AnswerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;


    @Autowired
    public AnswerServiceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;

    }

    @Override
    public boolean create(Answer answer) {
        return answerRepository.save(answer).getId() != 0;
    }

    @Override
    public boolean update(Answer answer) {
        return answerRepository.save(answer).getId() != 0;
    }

    @Override
    public boolean delete(Answer answer) {
        answerRepository.delete(answer);
        return true;
    }

    @Override
    public Answer findById(int id) {
        return answerRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Such Answer with ID(" + id + ") not was found"));
    }

    @Override
    public List<Answer> findAll() {
        return answerRepository.findAll();
    }

    @Override
    public List<Answer> findAllByUserId(Integer user_id) {
        return answerRepository.findAllByUserId(user_id).orElseThrow(() -> new NoSuchElementException("list answers from user with id(" + user_id + ") was not found"));
    }
}