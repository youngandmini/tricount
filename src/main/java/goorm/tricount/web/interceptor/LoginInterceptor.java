package goorm.tricount.web.interceptor;

import goorm.tricount.exception.UserUnauthorizedException;
import goorm.tricount.web.common.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(false);
        log.info("로그인 체크");

        if (session == null || session.getAttribute(SessionConst.LOGIN_USER_ID) == null) {
            throw new UserUnauthorizedException();
        }

        return true;
    }
}
