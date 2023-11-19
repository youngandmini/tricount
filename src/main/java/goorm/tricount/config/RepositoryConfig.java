package goorm.tricount.config;

import goorm.tricount.repository.expense.ExpenseJpaRepository;
import goorm.tricount.repository.expense.ExpenseRepositoryJpaImpl;
import goorm.tricount.repository.expense.ExpenseRepository;
import goorm.tricount.repository.settlement.SettlementJpaRepository;
import goorm.tricount.repository.settlement.SettlementRepositoryJpaImpl;
import goorm.tricount.repository.settlement.SettlementRepository;
import goorm.tricount.repository.user.UserJpaRepository;
import goorm.tricount.repository.user.UserRepositoryJpaImpl;
import goorm.tricount.repository.user.UserRepository;
import goorm.tricount.repository.usersettlement.UserSettlementJpaRepository;
import goorm.tricount.repository.usersettlement.UserSettlementRepositoryJpaImpl;
import goorm.tricount.repository.usersettlement.UserSettlementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RepositoryConfig {

    private final UserJpaRepository userJpaRepository;
    private final SettlementJpaRepository settlementJpaRepository;
    private final ExpenseJpaRepository expenseJpaRepository;
    private final UserSettlementJpaRepository userSettlementJpaRepository;

    @Bean
    public UserRepository userRepository() {

        return new UserRepositoryJpaImpl(userJpaRepository);
    }
    @Bean
    public SettlementRepository settlementRepository() {
        return new SettlementRepositoryJpaImpl(settlementJpaRepository);
    }
    @Bean
    public ExpenseRepository expenseRepository() {
        return new ExpenseRepositoryJpaImpl(expenseJpaRepository);
    }
    @Bean
    public UserSettlementRepository userSettlementRepository() {
        return new UserSettlementRepositoryJpaImpl(userSettlementJpaRepository);
    }
}
