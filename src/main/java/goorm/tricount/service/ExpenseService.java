package goorm.tricount.service;

import goorm.tricount.domain.Expense;
import goorm.tricount.domain.Settlement;
import goorm.tricount.domain.User;
import goorm.tricount.domain.UserSettlement;
import goorm.tricount.dto.BalanceResponse;
import goorm.tricount.dto.ExpenseCreateRequest;
import goorm.tricount.repository.expense.ExpenseRepository;
import goorm.tricount.repository.settlement.SettlementRepository;
import goorm.tricount.repository.user.UserRepository;
import goorm.tricount.repository.usersettlement.UserSettlementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final SettlementRepository settlementRepository;
    private final UserSettlementRepository userSettlementRepository;

    public Long createExpense(ExpenseCreateRequest request, Long settlementId, Long userId) {

        User currentUser = userRepository.find(userId).orElseThrow();
        Settlement settlement = settlementRepository.find(settlementId).orElseThrow();

        Expense expense = new Expense(request.getExpenseName(), request.getExpensePrice(), request.getExpenseDate(), currentUser, settlement);
        expenseRepository.save(expense);

        return expense.getId();
    }

    public List<BalanceResponse> calculateBalance(Long settlementId) {

        Settlement settlement = settlementRepository.findByIdWithExpense(settlementId).orElseThrow();
        List<User> users = userSettlementRepository.findUsersBySettlementId(settlementId);
        List<Expense> expenseList = settlement.getExpenseList();

        BigDecimal total = settlement.getExpenseList().stream().map(Expense::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal average = total.divide(BigDecimal.valueOf(users.size()), RoundingMode.HALF_UP);

    }

    private List<BalanceResponse> doCalculate(List<Expense> expenseList, BigDecimal average) {
        List<BalanceResponse> result = new ArrayList<>();

        Map<User, BigDecimal> balanceMap = new HashMap<>();
        for (Expense expense : expenseList) {
            BigDecimal subtracted = expense.getPrice().subtract(average);
            if (subtracted.intValue() != 0) {
                balanceMap.put(expense.getPaidBy(), subtracted);
            }
        }

        return result;
    }

}
