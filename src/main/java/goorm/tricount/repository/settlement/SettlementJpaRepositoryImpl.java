package goorm.tricount.repository.settlement;

import goorm.tricount.domain.Settlement;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SettlementJpaRepositoryImpl implements SettlementRepository {

    private final SettlementJpaRepository settlementJpaRepository;

    @Override
    public Long saveSettlement(Settlement settlement) {
        Settlement savedSettlement = settlementJpaRepository.save(settlement);
        return savedSettlement.getId();
    }
}
