package goorm.tricount.repository.usersettlement;

import goorm.tricount.domain.Settlement;
import goorm.tricount.domain.User;
import goorm.tricount.domain.UserSettlement;

import java.util.List;

public interface UserSettlementRepository {

    Long save(UserSettlement userSettlement);

    List<User> findUsersBySettlementId(Long settlementId);

    List<Settlement> findSettlementsByUserId(Long userId);
}
