package net.backend.questions.softarextask.repository;

import net.backend.questions.softarextask.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
  @Query("select u from User u where u.email = ?1")
  Optional <User> findByEmail(String email);

  Boolean existsUserByEmail(String email);
}
