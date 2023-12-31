package goorm.tricount.repository.user;


import goorm.tricount.domain.User;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserRepositoryJpaImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Long save(User user) {
        User savedUser = userJpaRepository.save(user);
        return savedUser.getId();
    }

    @Override
    public Optional<User> find(Long userId) {

        return userJpaRepository.findById(userId);
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        return userJpaRepository.findByUsernameAndPassword(username, password);
    }
}
