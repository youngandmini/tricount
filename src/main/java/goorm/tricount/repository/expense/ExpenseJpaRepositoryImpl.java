package goorm.tricount.repository.expense;

import goorm.tricount.domain.Expense;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExpenseJpaRepositoryImpl implements ExpenseRepository {

    private final ExpenseJpaRepository expenseJpaRepository;

    @Override
    public Long save(Expense expense) {
        Expense savedExpense = expenseJpaRepository.save(expense);
        return savedExpense.getId();
    }
}
