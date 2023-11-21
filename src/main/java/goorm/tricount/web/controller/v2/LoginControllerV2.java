package goorm.tricount.web.controller.v2;


import goorm.tricount.dto.UserLoginRequest;
import goorm.tricount.dto.UserSignupRequest;
import goorm.tricount.service.LoginService;
import goorm.tricount.web.apiresponse.ApiResponse;
import goorm.tricount.web.common.LoginSessionManager;
import goorm.tricount.web.common.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v2")
public class LoginControllerV2 {

    private final LoginService loginService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> signup(@RequestBody UserSignupRequest signupRequest) {
        log.info("새로운 회원가입 요청 발생. 유저 아이디: {}", signupRequest.getNickname());
        loginService.signup(signupRequest);
        log.info("새로운 회원가입 요청 수락. 유저 아이디: {}", signupRequest.getNickname());
        return ApiResponse.ok(null);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> login(@RequestBody UserLoginRequest loginRequest, HttpServletRequest request) {

        log.info("로그인 요청 발생. 로그인 시도한 유저: {}", loginRequest.getUsername());
        Long loginUserId = loginService.login(loginRequest);
        HttpSession session = request.getSession(true);
        session.setAttribute(SessionConst.LOGIN_USER_ID, loginUserId);
        log.info("로그인 요청 수락. 유저 식별자: {}", loginUserId);
        return ApiResponse.ok(null);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Long loginMemberId = null;
        if (session != null) {
            loginMemberId = (Long) session.getAttribute(SessionConst.LOGIN_USER_ID);
        }
        log.info("로그아웃 요청 발생. 유저 식별자: {}", loginMemberId);
        if (session != null) {
            session.invalidate();
        }
        log.info("로그아웃 요청 수락. 유저 식별자: {}", loginMemberId);
        return ApiResponse.ok(null);
    }
}
