package net.backend.questions.softarextask.repository;

import net.backend.questions.softarextask.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Page<Question> findAllByUserId(Integer userId, Pageable pageable);

    Optional<Question> findByIdAndUserId(Integer id, Integer userId);
}
