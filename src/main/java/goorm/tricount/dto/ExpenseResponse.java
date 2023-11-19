package goorm.tricount.dto;

import goorm.tricount.domain.Expense;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExpenseResponse {

    private Long expenseId;
    private String expenseName;
    private BigDecimal expensePrice;
    private LocalDate expensedDate;
    private String paidBy;
    private Long settlementId;

    private static ExpenseResponse of(Expense expense) {
        return new ExpenseResponse(
                expense.getId(),
                expense.getName(),
                expense.getPrice(),
                expense.getExpensedDate(),
                expense.getPaidBy().getNickname(),
                expense.getSettlement().getId()
        );
    }

    public static List<ExpenseResponse> listOf(List<Expense> expenseList) {
        return expenseList.stream().map(ExpenseResponse::of).collect(Collectors.toList());
    }
}
