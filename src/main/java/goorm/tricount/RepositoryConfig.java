package goorm.tricount;

import goorm.tricount.repository.user.UserJpaRepository;
import goorm.tricount.repository.user.UserJpaRepositoryImpl;
import goorm.tricount.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RepositoryConfig {

    @Autowired
    private final UserJpaRepository userJpaRepository;

    @Bean
    public UserRepository userRepository() {
        return new UserJpaRepositoryImpl(userJpaRepository);
    }
}
