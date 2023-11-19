package goorm.tricount.repository.settlement;

import goorm.tricount.domain.Settlement;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class SettlementRepositoryJpaImpl implements SettlementRepository {

    private final SettlementJpaRepository settlementJpaRepository;

    @Override
    public Long save(Settlement settlement) {
        Settlement savedSettlement = settlementJpaRepository.save(settlement);
        return savedSettlement.getId();
    }

    @Override
    public Optional<Settlement> find(Long settlementId) {
        return settlementJpaRepository.findById(settlementId);
    }

    @Override
    public void delete(Settlement deleteSettlement) {
        settlementJpaRepository.delete(deleteSettlement);
    }

    @Override
    public Optional<Settlement> findByIdWithExpense(Long settlementId) {
        return settlementJpaRepository.findByIdWithExpense(settlementId);
    }
}
