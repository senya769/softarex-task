package net.backend.questions.softarextask.service.impl;

import net.backend.questions.softarextask.dto.QuestionDto;
import net.backend.questions.softarextask.model.Question;
import net.backend.questions.softarextask.repository.QuestionRepository;
import net.backend.questions.softarextask.service.QuestionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, ModelMapper modelMapper) {
        this.questionRepository = questionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Question create(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public boolean update(Question question) {
        return questionRepository.save(question).getId()!= null;
    }

    @Override
    public boolean delete(Question question) {
        if(questionRepository.findById(question.getId()).isPresent()){
         questionRepository.delete(question);
         return true;
        }
        return false;
    }

    @Override
    public Optional<Question> findById(int id) {
        return questionRepository.findById(id);
    }

    @Override
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public List<QuestionDto> findAllByUserId(Integer user_id) {
      return questionRepository.findAllByUserId(user_id).stream()
                .map(question -> modelMapper.map(question,QuestionDto.class)).toList();
    }
}
