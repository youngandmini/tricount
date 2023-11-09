package goorm.tricount.repository.settlement;

import goorm.tricount.domain.Settlement;

public interface SettlementRepository {

    Long saveSettlement(Settlement settlement);
}
