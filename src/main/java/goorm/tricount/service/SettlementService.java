package goorm.tricount.service;


import goorm.tricount.domain.Settlement;
import goorm.tricount.domain.User;
import goorm.tricount.domain.UserSettlement;
import goorm.tricount.dto.SettlementCreateRequest;
import goorm.tricount.dto.SimpleSettlementResponse;
import goorm.tricount.dto.SettlementResponse;
import goorm.tricount.exception.AlreadyJoinSettlementException;
import goorm.tricount.exception.ForbiddenException;
import goorm.tricount.exception.ResourceNotFoundException;
import goorm.tricount.exception.UnexpectedException;
import goorm.tricount.repository.settlement.SettlementRepository;
import goorm.tricount.repository.user.UserRepository;
import goorm.tricount.repository.usersettlement.UserSettlementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SettlementService {

    private final SettlementRepository settlementRepository;
    private final UserRepository userRepository;
    private final UserSettlementRepository userSettlementRepository;

    public SettlementResponse createSettlement(SettlementCreateRequest request, Long loginUserId) {

        User currentUser = userRepository.find(loginUserId).orElseThrow(UnexpectedException::new);

        Settlement settlement = new Settlement(request.getSettlementName(), currentUser);
        settlementRepository.save(settlement);

        // settlement를 만든 회원은 settlement에 자동 가입된다.
        UserSettlement userSettlement = new UserSettlement(currentUser, settlement);
        userSettlementRepository.save(userSettlement);

        return SettlementResponse.of(settlement);
    }

    public SettlementResponse getSettlement(Long settlementId, Long loginUserId) {

        Settlement findSettlement = settlementRepository.findByIdWithExpense(settlementId).orElseThrow(ResourceNotFoundException::new);
        UserSettlement userSettlement = userSettlementRepository.findBySettlementIdAndUserId(settlementId, loginUserId)
                .orElseThrow(ForbiddenException::new);

        return SettlementResponse.of(findSettlement);
    }

    public void joinSettlement(Long settlementId, Long loginUserId) {

        User joinUser = userRepository.find(loginUserId).orElseThrow();
        Settlement joinSettlement = settlementRepository.find(settlementId).orElseThrow();
        Optional<UserSettlement> findUserSettlement = userSettlementRepository.findBySettlementIdAndUserId(settlementId, loginUserId);

        if (findUserSettlement.isPresent()) {
            throw new AlreadyJoinSettlementException();
        }

        UserSettlement userSettlement = new UserSettlement(joinUser, joinSettlement);
        userSettlementRepository.save(userSettlement);
    }


    public List<SimpleSettlementResponse> getSettlementList(Long loginUserId) {

        List<UserSettlement> userSettlementList = userSettlementRepository.findByUserId(loginUserId);
        List<Settlement> settlementList = userSettlementList.stream().map(UserSettlement::getSettlement).toList();

        return SimpleSettlementResponse.listOf(settlementList);
    }

    public void deleteSettlement(Long settlementId, Long loginUserId) {

        Settlement deleteSettlement = settlementRepository.find(settlementId).orElseThrow(ResourceNotFoundException::new);

        // settlement의 owner만 삭제할 수 있다.
        if (!deleteSettlement.getOwner().getId().equals(loginUserId)) {
            throw new ForbiddenException();
        }

        settlementRepository.delete(deleteSettlement);
        // settlement를 삭제할 떄 userSettlement / expense 들도 함께 삭제돼야함
    }

}
