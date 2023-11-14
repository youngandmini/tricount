package goorm.tricount.dto;


import goorm.tricount.domain.Settlement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SettlementCreateRequest {

    private String settlementName;

    public Settlement toEntity() {
        return new Settlement(settlementName);
    }
}
