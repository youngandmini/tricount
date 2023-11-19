package goorm.tricount.service;

import goorm.tricount.domain.User;
import goorm.tricount.dto.UserLoginRequest;
import goorm.tricount.dto.UserSignupRequest;
import goorm.tricount.exception.LoginFailureException;
import goorm.tricount.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    public void signup(UserSignupRequest request) {
        User user = new User(request.getUsername(), request.getPassword(), request.getNickname());
        userRepository.save(user);
    }

    public Long login(UserLoginRequest request) {
        User loginUser = userRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword())
                .orElseThrow(LoginFailureException::new);
        return loginUser.getId();
    }

}
