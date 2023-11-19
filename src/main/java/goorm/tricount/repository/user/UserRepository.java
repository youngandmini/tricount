package goorm.tricount.repository.user;

import goorm.tricount.domain.User;

import java.util.Optional;

public interface UserRepository {

    Long save(User user);

    Optional<User> find(Long userId);

    Optional<User> findByUsernameAndPassword(String username, String password);
}
