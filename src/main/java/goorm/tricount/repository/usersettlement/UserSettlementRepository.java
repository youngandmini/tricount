package goorm.tricount.repository.usersettlement;

import goorm.tricount.domain.Settlement;
import goorm.tricount.domain.User;
import goorm.tricount.domain.UserSettlement;

import java.util.List;
import java.util.Optional;

public interface UserSettlementRepository {

    Long save(UserSettlement userSettlement);

    Optional<UserSettlement> findBySettlementIdAndUserId(Long settlementId, Long userId);

    List<UserSettlement> findByUserId(Long userId);
}
