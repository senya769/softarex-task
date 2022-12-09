package net.backend.questions.softarextask.repository;

import net.backend.questions.softarextask.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    @Query("select q from Question q where q.user.id = ?1 order by q.id")
    Optional<Set<Question>> findAllByUserId(Integer userId);

    Optional<Question> findByIdAndUserId(Integer id, Integer userId);
}
