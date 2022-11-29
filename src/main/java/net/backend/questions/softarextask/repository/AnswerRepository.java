package net.backend.questions.softarextask.repository;

import net.backend.questions.softarextask.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    @Query("select a from Answer a where a.user.id = ?1")
    Optional<Set<Answer>> findAllByUserId(Integer userId);

    Optional<Answer> findByQuestionId(Integer questionId);

    Optional<Answer> findByIdAndUserId(Integer id, Integer userId);
}
