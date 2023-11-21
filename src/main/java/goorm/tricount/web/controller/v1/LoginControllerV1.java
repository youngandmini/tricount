package goorm.tricount.web.controller.v1;


import goorm.tricount.web.apiresponse.ApiResponse;
import goorm.tricount.web.common.LoginSessionManager;
import goorm.tricount.dto.UserLoginRequest;
import goorm.tricount.dto.UserSignupRequest;
import goorm.tricount.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class LoginControllerV1 {

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
    public ApiResponse<Void> login(@RequestBody UserLoginRequest loginRequest, HttpServletResponse response) {

        log.info("로그인 요청 발생. 로그인 시도한 유저: {}", loginRequest.getUsername());
        Long loginUserId = loginService.login(loginRequest);
        LoginSessionManager.createSession(loginUserId, response);
        log.info("로그인 요청 수락. 유저 식별자: {}", loginUserId);
        return ApiResponse.ok(null);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> logout(HttpServletRequest request) {

        log.info("로그아웃 요청 발생. 유저 식별자: {}", LoginSessionManager.getSession(request));
        LoginSessionManager.expireSession(request);
        log.info("로그아웃 요청 수락. 유저 식별자: {}", LoginSessionManager.getSession(request));
        return ApiResponse.ok(null);
    }
}
