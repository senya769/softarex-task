package net.backend.questions.softarextask.repository;

import net.backend.questions.softarextask.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer,Integer> {
}
