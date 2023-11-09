package goorm.tricount;

import goorm.tricount.repository.settlement.SettlementJpaRepository;
import goorm.tricount.repository.settlement.SettlementJpaRepositoryImpl;
import goorm.tricount.repository.settlement.SettlementRepository;
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

    private final UserJpaRepository userJpaRepository;
    private final SettlementJpaRepository settlementJpaRepository;

    @Bean
    public UserRepository userRepository() {
        return new UserJpaRepositoryImpl(userJpaRepository);
    }

    @Bean
    public SettlementRepository settlementRepository() {
        return new SettlementJpaRepositoryImpl(settlementJpaRepository);
    }
}
