package net.backend.questions.softarextask.repository;

import net.backend.questions.softarextask.model.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    Page<Answer> findAllByUserId(Integer userId, Pageable pageable);

    Optional<Answer> findByQuestionId(Integer questionId);

    Optional<Answer> findByIdAndUserId(Integer id, Integer userId);
}
