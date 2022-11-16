package net.backend.questions.softarextask.repository;

import net.backend.questions.softarextask.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Integer> {
}
