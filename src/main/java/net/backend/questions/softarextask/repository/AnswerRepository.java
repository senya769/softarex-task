package net.backend.questions.softarextask.repository;

import net.backend.questions.softarextask.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer,Integer> {
    @Query("select a from Answer a where a.user.id = ?1")
    Optional<List<Answer>> findAllByUserId(Integer user_id);
}
