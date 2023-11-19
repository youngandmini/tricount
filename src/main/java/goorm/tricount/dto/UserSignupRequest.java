package goorm.tricount.dto;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserSignupRequest {

    private String username;
    private String password;
    private String nickname;
}
