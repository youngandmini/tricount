package goorm.tricount.web.controller;

import goorm.tricount.dto.SettlementCreateRequest;
import goorm.tricount.dto.SimpleSettlementResponse;
import goorm.tricount.dto.SettlementResponse;
import goorm.tricount.exception.UserUnauthorizedException;
import goorm.tricount.service.SettlementService;
import goorm.tricount.web.apiresponse.ApiResponse;
import goorm.tricount.web.common.LoginSessionManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/settlements")
public class SettlementController {

    private final SettlementService settlementService;

    @PostMapping
    public ApiResponse<SettlementResponse> createSettlement(@RequestBody SettlementCreateRequest settlementCreateRequest,
                                                            HttpServletRequest request) {
        Long loginUserId = LoginSessionManager.getSession(request);
        if (loginUserId == null) {
            throw new UserUnauthorizedException();
        }
        log.info("새로운 정산방 생성 요청 발생. 요청한 유저 아이디: {}", loginUserId);
        SettlementResponse response = settlementService.createSettlement(settlementCreateRequest, loginUserId);
        log.info("새로운 정산방 생성 요청 수락. 요청한 유저 아이디: {}", loginUserId);

        return ApiResponse.ok(response);
    }

    @GetMapping("/{settlementId}")
    public ApiResponse<SettlementResponse> getSettlement(@PathVariable("settlementId") Long settlementId,
                                                         HttpServletRequest request) {
        Long loginUserId = LoginSessionManager.getSession(request);
        if (loginUserId == null) {
            throw new UserUnauthorizedException();
        }
        log.info("정산방 [{}] 조회 요청 수락. 요청한 유저 아이디: {}", settlementId, loginUserId);
        SettlementResponse response = settlementService.getSettlement(settlementId, loginUserId);
        log.info("정산방 [{}] 조회 요청 수락. 요청한 유저 아이디: {}", settlementId, loginUserId);
        return ApiResponse.ok(response);
    }

    @PostMapping("/{settlementId}")
    public ApiResponse<Void> joinSettlement(@PathVariable("settlementId") Long settlementId, HttpServletRequest request) {

        Long loginUserId = LoginSessionManager.getSession(request);
        if (loginUserId == null) {
            throw new UserUnauthorizedException();
        }
        log.info("정산방 [{}] 가입 요청 발생. 요청한 유저 아이디: {}", settlementId, loginUserId);
        settlementService.joinSettlement(settlementId, loginUserId);
        log.info("정산방 [{}] 가입 요청 수락. 요청한 유저 아이디: {}", settlementId, loginUserId);

        return ApiResponse.ok(null);
    }

    @GetMapping
    public ApiResponse<List<SimpleSettlementResponse>> getSettlementList(HttpServletRequest request) {

        Long loginUserId = LoginSessionManager.getSession(request);
        if (loginUserId == null) {
            throw new UserUnauthorizedException();
        }
        log.info("정산방 리스트 조회 요청 발생. 요청한 유저 아이디: {}", loginUserId);
        List<SimpleSettlementResponse> response = settlementService.getSettlementList(loginUserId);
        log.info("정산방 리스트 조회 요청 수락. 요청한 유저 아이디: {}", loginUserId);

        return ApiResponse.ok(response);
    }

    @DeleteMapping("/{settlementId}")
    public ApiResponse<Void> deleteSettlement(@PathVariable("settlementId") Long settlementId, HttpServletRequest request) {

        Long loginUserId = LoginSessionManager.getSession(request);
        if (loginUserId == null) {
            throw new UserUnauthorizedException();
        }
        log.info("정산방 [{}] 삭제 요청 발생. 요청한 유저 아이디: {}", settlementId, loginUserId);
        settlementService.deleteSettlement(settlementId, loginUserId);
        log.info("정산방 [{}] 삭제 요청 수락. 요청한 유저 아이디: {}", settlementId, loginUserId);

        return ApiResponse.ok(null);
    }
}
