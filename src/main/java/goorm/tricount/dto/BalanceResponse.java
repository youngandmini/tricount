package goorm.tricount.dto;


import goorm.tricount.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class BalanceResponse {

    private Long senderUserId;
    private String senderUsername;
    private Long receiverUserId;
    private String receiverUsername;
    private BigDecimal sendAmount;

    public static BalanceResponse of(User sender, User receiver, BigDecimal sendAmount) {
        return null;
    }
}
