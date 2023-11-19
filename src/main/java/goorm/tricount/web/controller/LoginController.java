package goorm.tricount.web.controller;


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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> signup(@RequestBody UserSignupRequest signupRequest) {
        loginService.signup(signupRequest);
        log.info("새로운 회원가입 요청. 유저 아이디: {}", signupRequest.getNickname());
        return ApiResponse.ok(null);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> login(@RequestBody UserLoginRequest loginRequest, HttpServletResponse response) {
        Long loginUserId = loginService.login(loginRequest);
        LoginSessionManager.createSession(loginUserId, response);

        log.info("로그인 요청 발생. 유저 식별자: {}", loginUserId);
        return ApiResponse.ok(null);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> logout(HttpServletRequest request) {

        log.info("로그아웃 요청 발생. 유저 식별자: {}", LoginSessionManager.getSession(request));
        LoginSessionManager.expireSession(request);

        return ApiResponse.ok(null);
    }
}
