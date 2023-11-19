package goorm.tricount.service;

import goorm.tricount.domain.*;
import goorm.tricount.dto.BalanceResponse;
import goorm.tricount.dto.ExpenseAddRequest;
import goorm.tricount.exception.ForbiddenException;
import goorm.tricount.exception.ResourceNotFoundException;
import goorm.tricount.exception.UnexpectedException;
import goorm.tricount.repository.expense.ExpenseRepository;
import goorm.tricount.repository.settlement.SettlementRepository;
import goorm.tricount.repository.user.UserRepository;
import goorm.tricount.repository.usersettlement.UserSettlementRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final SettlementRepository settlementRepository;
    private final UserSettlementRepository userSettlementRepository;

    public Long createExpense(ExpenseAddRequest request, Long settlementId, Long loginUserId) {

        User loginUser = userRepository.find(loginUserId).orElseThrow(UnexpectedException::new);
        Settlement settlement = settlementRepository.find(settlementId).orElseThrow(ResourceNotFoundException::new);

        if (userSettlementRepository.findBySettlementIdAndUserId(settlementId, loginUserId).isEmpty()) {
            throw new ForbiddenException();
        }

        Expense expense = new Expense(request.getExpenseName(), request.getExpensePrice(), request.getExpensedDate(), loginUser, settlement);
        expenseRepository.save(expense);

        return expense.getId();
    }

    public void deleteExpense(Long settlementId, Long expenseId, Long loginUserId) {

        Expense expense = expenseRepository.find(expenseId).orElseThrow(ResourceNotFoundException::new);

        if (!expense.getSettlement().getId().equals(settlementId)) {
            throw new ResourceNotFoundException();
        }

        if (!expense.getPaidBy().getId().equals(loginUserId)) {
            throw new ForbiddenException();
        }
        expenseRepository.delete(expense);
    }

    public List<BalanceResponse> calculateBalance(Long settlementId, Long loginUserId) {

        User loginUser = userRepository.find(loginUserId).orElseThrow(UnexpectedException::new);
        Settlement settlement = settlementRepository.findByIdWithUserSettlement(settlementId).orElseThrow(ResourceNotFoundException::new);

        List<User> users = settlement.getUserSettlementList().stream().map(UserSettlement::getUser).toList();
        if (!users.contains(loginUser)) {
            throw new ForbiddenException();
        }
        //지연로딩 이용
        List<Expense> expenseList = settlement.getExpenseList();

        return BalanceResponse.listOf(doCalculate(expenseList, users));
    }

    private List<BalanceResult> doCalculate(List<Expense> expenseList, List<User> users) {

        for (Expense expense : expenseList) {
            log.info("정산 대상: {}, {}", expense.getName(), expense.getPrice());
        }

        // 평균 구하기
        BigDecimal total = expenseList.stream().map(Expense::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal average = total.divide(BigDecimal.valueOf(users.size()), RoundingMode.HALF_UP);
        log.info("정산 총액, 정산 평균 금액: {} {}", total, average);

        List<BalanceResult> result = new ArrayList<>();

        Map<User, BigDecimal> balanceMap = new HashMap<>();
        for (Expense expense : expenseList) {
            balanceMap.put(expense.getPaidBy(), expense.getPrice().add(balanceMap.getOrDefault(expense.getPaidBy(), BigDecimal.valueOf(0))));
        }
        for (User user : balanceMap.keySet()) {
            log.info("유저 당 지불한 금액: {} {}", user.getNickname(), balanceMap.get(user));
        }

        List<ExpenseDifference> differenceList = new ArrayList<>();
        for (User user : users) {
            BigDecimal difference = balanceMap.getOrDefault(user, BigDecimal.valueOf(0)).subtract(average);
            if (!difference.equals(BigDecimal.ZERO)) {
                differenceList.add(new ExpenseDifference(user, difference));
            }
        }

        while (true) {
            Collections.sort(differenceList);
            if (differenceList.size() < 2) {
                break;
            }
            if (differenceList.size() == 2) {
                result.add(new BalanceResult(differenceList.get(0).getUser(), differenceList.get(1).getUser(), differenceList.get(0).getDifference().multiply(BigDecimal.valueOf(-1))));
                break;
            }
            ExpenseDifference minDifference = differenceList.get(0);
            ExpenseDifference maxDifference = differenceList.get(differenceList.size() - 1);
            int compared = minDifference.getDifference().add(maxDifference.getDifference()).compareTo(BigDecimal.ZERO);

            if (compared == 0) {    // A가 줄 돈과 B가 받을 돈이 일치
                result.add(new BalanceResult(minDifference.getUser(), maxDifference.getUser(), maxDifference.getDifference()));

                // 최대와 최소를 둘 다 삭제
                differenceList.remove(differenceList.get(differenceList.size() - 1));
                differenceList.remove(differenceList.get(0));
            } else if (compared > 0) {  // A가 줄 돈보다 B가 받을 돈이 많음
                result.add(new BalanceResult(minDifference.getUser(), maxDifference.getUser(), minDifference.getDifference().multiply(BigDecimal.valueOf(-1))));

                //최소만 삭제하고 최대는 값 업데이트
                maxDifference.setDifference(maxDifference.getDifference().add(minDifference.getDifference()));
                differenceList.remove(differenceList.get(0));
            } else { // A가 줄 돈이 B가 받을 돈보다 많음
                result.add(new BalanceResult(minDifference.getUser(), maxDifference.getUser(), maxDifference.getDifference()));

                //최대만 삭제하고 최소는 값 업데이트
                minDifference.setDifference(minDifference.getDifference().add(maxDifference.getDifference()));
                differenceList.remove(differenceList.get(differenceList.size() - 1));
            }
        }

        return result;
    }

    @Getter
    private static class ExpenseDifference implements Comparable<ExpenseDifference> {
        private User user;
        private BigDecimal difference;

        public ExpenseDifference(User user, BigDecimal difference) {
            this.user = user;
            this.difference = difference;
        }

        public void setDifference(BigDecimal difference) {
            this.difference = difference;
        }

        @Override
        public int compareTo(ExpenseDifference o) {
            return this.difference.compareTo(o.difference);
        }
    }
}
