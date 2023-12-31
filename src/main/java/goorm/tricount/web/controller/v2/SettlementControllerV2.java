package goorm.tricount.web.controller.v2;

import goorm.tricount.dto.*;
import goorm.tricount.exception.UserUnauthorizedException;
import goorm.tricount.service.ExpenseService;
import goorm.tricount.service.SettlementService;
import goorm.tricount.web.apiresponse.ApiResponse;
import goorm.tricount.web.common.LoginSessionManager;
import goorm.tricount.web.common.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/v2/settlements")
public class SettlementControllerV2 {

    private final SettlementService settlementService;
    private final ExpenseService expenseService;

    @PostMapping
    public ApiResponse<SettlementResponse> createSettlement(@RequestBody SettlementCreateRequest settlementCreateRequest,
                                                            @SessionAttribute(name= SessionConst.LOGIN_USER_ID) Long loginUserId) {

        log.info("새로운 정산방 생성 요청 발생. 요청한 유저 아이디: {}", loginUserId);
        SettlementResponse response = settlementService.createSettlement(settlementCreateRequest, loginUserId);
        log.info("새로운 정산방 생성 요청 수락. 요청한 유저 아이디: {}", loginUserId);

        return ApiResponse.ok(response);
    }

    @GetMapping("/{settlementId}")
    public ApiResponse<SettlementResponse> getSettlement(@PathVariable("settlementId") Long settlementId,
                                                         @SessionAttribute(name= SessionConst.LOGIN_USER_ID) Long loginUserId) {

        log.info("정산방 [{}] 조회 요청 수락. 요청한 유저 아이디: {}", settlementId, loginUserId);
        SettlementResponse response = settlementService.getSettlement(settlementId, loginUserId);
        log.info("정산방 [{}] 조회 요청 수락. 요청한 유저 아이디: {}", settlementId, loginUserId);
        return ApiResponse.ok(response);
    }

    @PostMapping("/{settlementId}")
    public ApiResponse<Void> joinSettlement(@PathVariable("settlementId") Long settlementId,
                                            @SessionAttribute(name= SessionConst.LOGIN_USER_ID) Long loginUserId) {

        log.info("정산방 [{}] 가입 요청 발생. 요청한 유저 아이디: {}", settlementId, loginUserId);
        settlementService.joinSettlement(settlementId, loginUserId);
        log.info("정산방 [{}] 가입 요청 수락. 요청한 유저 아이디: {}", settlementId, loginUserId);

        return ApiResponse.ok(null);
    }

    @GetMapping
    public ApiResponse<List<SimpleSettlementResponse>> getSettlementList(@SessionAttribute(name= SessionConst.LOGIN_USER_ID) Long loginUserId) {

        log.info("정산방 리스트 조회 요청 발생. 요청한 유저 아이디: {}", loginUserId);
        List<SimpleSettlementResponse> response = settlementService.getSettlementList(loginUserId);
        log.info("정산방 리스트 조회 요청 수락. 요청한 유저 아이디: {}", loginUserId);

        return ApiResponse.ok(response);
    }

    @DeleteMapping("/{settlementId}")
    public ApiResponse<Void> deleteSettlement(@PathVariable("settlementId") Long settlementId,
                                              @SessionAttribute(name= SessionConst.LOGIN_USER_ID) Long loginUserId) {

        log.info("정산방 [{}] 삭제 요청 발생. 요청한 유저 아이디: {}", settlementId, loginUserId);
        settlementService.deleteSettlement(settlementId, loginUserId);
        log.info("정산방 [{}] 삭제 요청 수락. 요청한 유저 아이디: {}", settlementId, loginUserId);

        return ApiResponse.ok(null);
    }

    @PostMapping("/{settlementId}/expenses")
    public ApiResponse<Void> addExpense(@RequestBody ExpenseAddRequest expenseAddRequest,
                                        @PathVariable("settlementId") Long settlementId,
                                        @SessionAttribute(name= SessionConst.LOGIN_USER_ID) Long loginUserId) {

        log.info("정산방 [{}] 지출 추가 요청 발생. 요청한 유저 아이디: {}", settlementId, loginUserId);
        expenseService.createExpense(expenseAddRequest, settlementId, loginUserId);
        log.info("정산방 [{}] 지출 추가 요청 수락. 요청한 유저 아이디: {}", settlementId, loginUserId);

        return ApiResponse.ok(null);
    }

    @DeleteMapping("/{settlementId}/expenses/{expenseId}")
    public ApiResponse<Void> deleteExpense(@PathVariable("settlementId") Long settlementId,
                                           @PathVariable("expenseId") Long expenseId,
                                           @SessionAttribute(name= SessionConst.LOGIN_USER_ID) Long loginUserId) {

        log.info("정산방 [{}] 지출 [{}] 삭제 요청 발생. 요청한 유저 아이디: {}", settlementId, expenseId, loginUserId);
        expenseService.deleteExpense(settlementId, expenseId, loginUserId);
        log.info("정산방 [{}] 지출 [{}] 삭제 요청 수락. 요청한 유저 아이디: {}", settlementId, expenseId, loginUserId);

        return ApiResponse.ok(null);
    }

    @GetMapping("/{settlementId}/balance")
    public ApiResponse<List<BalanceResponse>> getBalanceResult(@PathVariable("settlementId") Long settlementId,
                                                               @SessionAttribute(name= SessionConst.LOGIN_USER_ID) Long loginUserId) {

        log.info("정산방 [{}] 정산 결과 요청 발생. 요청한 유저 아이디: {}", settlementId, loginUserId);
        List<BalanceResponse> response = expenseService.calculateBalance(settlementId, loginUserId);
        log.info("정산방 [{}] 정산 결과 요청 수락. 요청한 유저 아이디: {}", settlementId, loginUserId);

        return ApiResponse.ok(response);
    }

}
