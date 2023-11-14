package goorm.tricount.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ExpenseCreateRequest {

    private String expenseName;
    private BigDecimal expensePrice;
    private LocalDate expenseDate;
}
