package goorm.tricount.repository.usersettlement;

import goorm.tricount.domain.UserSettlement;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserSettlementJpaRepositoryImpl implements UserSettlementRepository {

    private final UserSettlementJpaRepository userSettlementJpaRepository;

    @Override
    public Long saveUserSettlement(UserSettlement userSettlement) {
        UserSettlement savedUserSettlement = userSettlementJpaRepository.save(userSettlement);
        return savedUserSettlement.getId();
    }
}
