package goorm.tricount.repository.expense;

import goorm.tricount.domain.Expense;

import java.util.Optional;

public interface ExpenseRepository {

    Long save(Expense expense);

    Optional<Expense> find(Long expenseId);

    void delete(Expense expense);
}
