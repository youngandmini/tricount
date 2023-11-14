package goorm.tricount.repository.usersettlement;

import goorm.tricount.domain.Settlement;
import goorm.tricount.domain.User;
import goorm.tricount.domain.UserSettlement;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserSettlementJpaRepositoryImpl implements UserSettlementRepository {

    private final UserSettlementJpaRepository userSettlementJpaRepository;

    @Override
    public Long save(UserSettlement userSettlement) {
        UserSettlement savedUserSettlement = userSettlementJpaRepository.save(userSettlement);
        return savedUserSettlement.getId();
    }

    @Override
    public List<User> findUsersBySettlementId(Long settlementId) {
        return userSettlementJpaRepository.findUsersBySettlementId(settlementId);
    }

    @Override
    public List<Settlement> findSettlementsByUserId(Long userId) {
        return userSettlementJpaRepository.findSettlementsByUserId(userId);
    }
}
