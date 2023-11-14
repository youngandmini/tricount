package goorm.tricount.repository.expense;

import goorm.tricount.domain.Expense;

public interface ExpenseRepository {

    Long save(Expense expense);
}
