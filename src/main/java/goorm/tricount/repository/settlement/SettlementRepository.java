package goorm.tricount.repository.settlement;

import goorm.tricount.domain.Settlement;

import java.util.Optional;

public interface SettlementRepository {

    Long save(Settlement settlement);

    Optional<Settlement> find(Long settlementId);

    void delete(Settlement deleteSettlement);

    Optional<Settlement> findByIdWithExpense(Long settlementId);
}
