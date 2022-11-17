package net.backend.questions.softarextask.repository;

import net.backend.questions.softarextask.model.Answer;
import net.backend.questions.softarextask.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer,Integer> {
    List<Answer> findAllByUserId(Integer user_id);
}
