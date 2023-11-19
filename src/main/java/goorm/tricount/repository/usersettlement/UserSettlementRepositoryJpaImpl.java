package goorm.tricount.repository.usersettlement;

import goorm.tricount.domain.Settlement;
import goorm.tricount.domain.User;
import goorm.tricount.domain.UserSettlement;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserSettlementRepositoryJpaImpl implements UserSettlementRepository {

    private final UserSettlementJpaRepository userSettlementJpaRepository;

    @Override
    public Long save(UserSettlement userSettlement) {
        UserSettlement savedUserSettlement = userSettlementJpaRepository.save(userSettlement);
        return savedUserSettlement.getId();
    }

    @Override
    public Optional<UserSettlement> findBySettlementIdAndUserId(Long settlementId, Long userId) {
        return userSettlementJpaRepository.findBySettlementIdAndUserId(settlementId, userId);
    }

    @Override
    public List<UserSettlement> findByUserId(Long userId) {
        return userSettlementJpaRepository.findByUserId(userId);
    }
}
