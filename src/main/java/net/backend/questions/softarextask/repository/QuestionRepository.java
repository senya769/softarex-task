package net.backend.questions.softarextask.repository;

import net.backend.questions.softarextask.model.Answer;
import net.backend.questions.softarextask.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question,Integer> {
    @Query("select q from Question q where q.user.id = ?1")
    Optional<List<Question>> findAllByUserId(Integer userId);
    Optional<Question> findByIdAndUserId(Integer id, Integer userId);

}
