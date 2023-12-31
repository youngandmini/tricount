package goorm.tricount.dto;

import goorm.tricount.domain.Settlement;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SettlementResponse {

    private Long settlementId;
    private String settlementName;
    private String owner;
    private List<String> userList;
    private List<ExpenseResponse> expenseList;

    public static SettlementResponse of(Settlement settlement) {
        return new SettlementResponse(
                settlement.getId(),
                settlement.getName(),
                settlement.getOwner().getNickname(),
                settlement.getUserSettlementList().stream().map(us -> us.getUser().getNickname()).toList(),
                ExpenseResponse.listOf(settlement.getExpenseList())
        );
    }
}
