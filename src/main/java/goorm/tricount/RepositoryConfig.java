package goorm.tricount;

import goorm.tricount.repository.expense.ExpenseJpaRepository;
import goorm.tricount.repository.expense.ExpenseJpaRepositoryImpl;
import goorm.tricount.repository.expense.ExpenseRepository;
import goorm.tricount.repository.settlement.SettlementJpaRepository;
import goorm.tricount.repository.settlement.SettlementJpaRepositoryImpl;
import goorm.tricount.repository.settlement.SettlementRepository;
import goorm.tricount.repository.user.UserJpaRepository;
import goorm.tricount.repository.user.UserJpaRepositoryImpl;
import goorm.tricount.repository.user.UserRepository;
import goorm.tricount.repository.usersettlement.UserSettlementJpaRepository;
import goorm.tricount.repository.usersettlement.UserSettlementJpaRepositoryImpl;
import goorm.tricount.repository.usersettlement.UserSettlementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
        return new UserJpaRepositoryImpl(userJpaRepository);
    }
    @Bean
    public SettlementRepository settlementRepository() {
        return new SettlementJpaRepositoryImpl(settlementJpaRepository);
    }
    @Bean
    public ExpenseRepository expenseRepository() {
        return new ExpenseJpaRepositoryImpl(expenseJpaRepository);
    }
    @Bean
    public UserSettlementRepository userSettlementRepository() {
        return new UserSettlementJpaRepositoryImpl(userSettlementJpaRepository);
    }
}
