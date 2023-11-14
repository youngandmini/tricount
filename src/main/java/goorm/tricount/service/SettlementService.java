package goorm.tricount.service;


import goorm.tricount.domain.Settlement;
import goorm.tricount.domain.User;
import goorm.tricount.domain.UserSettlement;
import goorm.tricount.dto.SettlementCreateRequest;
import goorm.tricount.repository.settlement.SettlementRepository;
import goorm.tricount.repository.user.UserRepository;
import goorm.tricount.repository.usersettlement.UserSettlementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettlementService {

    private final SettlementRepository settlementRepository;
    private final UserRepository userRepository;
    private final UserSettlementRepository userSettlementRepository;

    public Long createSettlement(SettlementCreateRequest request, Long userId) {

        User currentUser = userRepository.find(userId).orElseThrow();

        Settlement settlement = new Settlement(request.getSettlementName(), currentUser);
        settlementRepository.save(settlement);

        // settlement를 만든 회원은 settlement에 자동 가입된다.
        UserSettlement userSettlement = new UserSettlement(currentUser, settlement);
        userSettlementRepository.save(userSettlement);

        return settlement.getId();
    }

    public void joinSettlement(Long settlementId, Long userId, Long loginUserId) {

        User joinUser = userRepository.find(userId).orElseThrow();
        Settlement joinSettlement = settlementRepository.find(settlementId).orElseThrow();

        // settlement의 owner만 초대할 수 있다.
        if (!joinSettlement.getOwner().getId().equals(loginUserId)) {
            throw new IllegalStateException();
        }

        UserSettlement userSettlement = new UserSettlement(joinUser, joinSettlement);
        userSettlementRepository.save(userSettlement);
    }

    public void deleteSettlement(Long settlementId, Long loginUserId) {

        Settlement deleteSettlement = settlementRepository.find(settlementId).orElseThrow();

        // settlement의 owner만 삭제할 수 있다.
        if (!deleteSettlement.getOwner().getId().equals(loginUserId)) {
            throw new IllegalStateException();
        }

        settlementRepository.delete(deleteSettlement);
        // settlement를 삭제할 떄 userSettlement 들도 함께 삭제돼야함

    }
}
