package goorm.tricount.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class BalanceResult {
    private User sender;
    private User receiver;
    private BigDecimal sendAmount;
}
