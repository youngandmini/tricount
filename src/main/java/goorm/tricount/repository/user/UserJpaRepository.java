package goorm.tricount.repository.user;

import goorm.tricount.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {


    Optional<User> findByUsernameAndPassword(String username, String password);
}
