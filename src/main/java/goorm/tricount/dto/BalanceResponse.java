package goorm.tricount.dto;


import goorm.tricount.domain.BalanceResult;
import goorm.tricount.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BalanceResponse {

    private Long senderUserId;
    private String senderUsername;
    private Long receiverUserId;
    private String receiverUsername;
    private Long sendAmount;

    public static BalanceResponse of(BalanceResult balanceResult) {
        return new BalanceResponse(
                balanceResult.getSender().getId(),
                balanceResult.getSender().getNickname(),
                balanceResult.getReceiver().getId(),
                balanceResult.getReceiver().getNickname(),
                balanceResult.getSendAmount().longValue());
    }

    public static List<BalanceResponse> listOf(List<BalanceResult> balanceResultList) {
        return balanceResultList.stream().map(BalanceResponse::of).collect(Collectors.toList());
    }
}
