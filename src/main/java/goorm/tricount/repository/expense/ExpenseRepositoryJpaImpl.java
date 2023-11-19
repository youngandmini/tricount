package goorm.tricount.repository.expense;

import goorm.tricount.domain.Expense;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ExpenseRepositoryJpaImpl implements ExpenseRepository {

    private final ExpenseJpaRepository expenseJpaRepository;

    @Override
    public Long save(Expense expense) {
        Expense savedExpense = expenseJpaRepository.save(expense);
        return savedExpense.getId();
    }

    @Override
    public Optional<Expense> find(Long expenseId) {
        return expenseJpaRepository.findById(expenseId);
    }

    @Override
    public void delete(Expense expense) {
        expenseJpaRepository.delete(expense);
    }
}
