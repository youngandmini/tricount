package goorm.tricount.dto;

import goorm.tricount.domain.Settlement;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SimpleSettlementResponse {

    private Long settlementId;
    private String settlementName;
    private String owner;

    private static SimpleSettlementResponse of(Settlement settlement) {
        return new SimpleSettlementResponse(
                settlement.getId(),
                settlement.getName(),
                settlement.getOwner().getNickname()
        );
    }

    public static List<SimpleSettlementResponse> listOf(List<Settlement> settlementList) {
        return settlementList.stream().map(SimpleSettlementResponse::of).collect(Collectors.toList());
    }
}
