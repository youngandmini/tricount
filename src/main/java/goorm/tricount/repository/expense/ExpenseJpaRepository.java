package goorm.tricount.repository.expense;

import goorm.tricount.domain.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseJpaRepository extends JpaRepository<Expense, Long> {

}
