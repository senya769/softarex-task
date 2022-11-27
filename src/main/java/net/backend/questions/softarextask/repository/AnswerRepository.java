package net.backend.questions.softarextask.repository;

import net.backend.questions.softarextask.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer,Integer> {
    @Query("select a from Answer a where a.user.id = ?1")
    Optional<List<Answer>> findAllByUserId(Integer userId);
    Optional<Answer> findByQuestionId(Integer questionId);
    Optional<Answer> findByIdAndUserId(Integer id, Integer userId);
}
