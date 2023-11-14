package goorm.tricount.repository.user;


import goorm.tricount.domain.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserJpaRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Long save(User user) {
        User savedUser = userJpaRepository.save(user);
        return savedUser.getId();
    }
}
